/**
 * Copyright (C) 2017 Etaia AS (oss@hubrick.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hubrick.vertx.elastictranscoder.client;

import com.google.common.base.Charsets;
import com.hubrick.vertx.elastictranscoder.signature.AWS4SignatureBuilder;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpConnection;
import io.vertx.core.http.HttpFrame;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

class ElasticTranscoderClientRequest implements HttpClientRequest {

    private static final Logger log = LoggerFactory.getLogger(ElasticTranscoderClientRequest.class);

    private final HttpClientRequest request;

    // These are actually set when the request is created, but we need to know
    private final String method;
    private final String region;
    private final String serviceName;
    private final Clock clock;
    private final boolean signPayload;

    // Used for authentication (which may be optional depending on the bucket)
    private String awsAccessKey;
    private String awsSecretKey;
    private boolean authenticationHeaderSet;

    public ElasticTranscoderClientRequest(String method,
                                          String region,
                                          String serviceName,
                                          HttpClientRequest request) {
        this(method, region, serviceName, request, null, null, Clock.systemDefaultZone(), false);
    }

    public ElasticTranscoderClientRequest(String method,
                                          String region,
                                          String serviceName,
                                          HttpClientRequest request,
                                          String awsAccessKey,
                                          String awsSecretKey,
                                          Clock clock,
                                          boolean signPayload) {
        checkNotNull(method, "method must not be null");
        checkNotNull(region, "region must not be null");
        checkNotNull(serviceName, "serviceName must not be null");
        checkNotNull(request, "request must not be null");
        checkNotNull(clock, "clock must not be null");

        this.method = method;
        this.region = region;
        this.serviceName = serviceName;
        this.request = request;
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
        this.clock = clock;
        this.signPayload = signPayload;
    }

    @Override
    public HttpClientRequest setFollowRedirects(boolean followRedirects) {
        return request.setFollowRedirects(followRedirects);
    }

    @Override
    public String absoluteURI() {
        return request.absoluteURI();
    }

    @Override
    public ElasticTranscoderClientRequest setWriteQueueMaxSize(int maxSize) {
        request.setWriteQueueMaxSize(maxSize);
        return this;
    }

    @Override
    public boolean writeQueueFull() {
        return request.writeQueueFull();
    }

    @Override
    public ElasticTranscoderClientRequest drainHandler(Handler<Void> handler) {
        request.drainHandler(handler);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest handler(Handler<HttpClientResponse> handler) {
        request.handler(handler);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest pause() {
        request.pause();
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest resume() {
        request.resume();
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest endHandler(Handler<Void> endHandler) {
        request.endHandler(endHandler);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest exceptionHandler(Handler<Throwable> handler) {
        request.exceptionHandler(handler);
        return this;
    }

    @Override
    public boolean isChunked() {
        return request.isChunked();
    }

    @Override
    public ElasticTranscoderClientRequest setChunked(boolean chunked) {
        request.setChunked(chunked);
        return this;
    }

    @Override
    public HttpMethod method() {
        return null;
    }

    @Override
    public String getRawMethod() {
        return request.getRawMethod();
    }

    @Override
    public ElasticTranscoderClientRequest setRawMethod(String s) {
        request.setRawMethod(s);
        return this;
    }

    @Override
    public String uri() {
        return request.uri();
    }

    @Override
    public String path() {
        return request.path();
    }

    @Override
    public String query() {
        return request.query();
    }

    @Override
    public String getHost() {
        return request.getHost();
    }

    @Override
    public ElasticTranscoderClientRequest setHost(String s) {
        request.setHost(s);
        return this;
    }

    @Override
    public MultiMap headers() {
        return request.headers();
    }

    @Override
    public ElasticTranscoderClientRequest putHeader(String name, String value) {
        request.putHeader(name, value);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest putHeader(CharSequence name, CharSequence value) {
        request.putHeader(name, value);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest putHeader(String name, Iterable<String> values) {
        request.putHeader(name, values);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest putHeader(CharSequence name, Iterable<CharSequence> values) {
        request.putHeader(name, values);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest write(Buffer chunk) {
        initAuthenticationHeaderBeforePayload();

        request.write(chunk);
        logBody(chunk);

        return this;
    }

    @Override
    public ElasticTranscoderClientRequest write(String chunk) {
        initAuthenticationHeaderBeforePayload();

        request.write(chunk);
        logBody(Buffer.buffer(chunk.getBytes()));

        return this;
    }

    @Override
    public ElasticTranscoderClientRequest write(String chunk, String enc) {
        initAuthenticationHeaderBeforePayload();

        request.write(chunk, enc);
        logBody(Buffer.buffer(chunk.getBytes()));

        return this;
    }

    @Override
    public ElasticTranscoderClientRequest continueHandler(Handler<Void> handler) {
        request.continueHandler(handler);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest sendHead() {
        initAuthenticationHeaderBeforePayload();

        request.sendHead();
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest sendHead(Handler<HttpVersion> handler) {
        initAuthenticationHeaderBeforePayload();

        request.sendHead(handler);
        return this;
    }

    @Override
    public void end(String chunk) {
        initAuthenticationHeader(Buffer.buffer(chunk));

        request.end(chunk);
        logBody(Buffer.buffer(chunk.getBytes()));
    }

    @Override
    public void end(String chunk, String enc) {
        initAuthenticationHeader(Buffer.buffer(chunk, enc));

        request.end(chunk, enc);
        logBody(Buffer.buffer(chunk.getBytes()));
    }

    @Override
    public void end(Buffer chunk) {
        initAuthenticationHeader(chunk);

        request.end(chunk);
        logBody(chunk);
    }

    @Override
    public void end() {
        initAuthenticationHeader(Buffer.buffer());

        request.end();
        logBody(Buffer.buffer());
    }

    @Override
    public ElasticTranscoderClientRequest setTimeout(long timeoutMs) {
        request.setTimeout(timeoutMs);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest pushHandler(Handler<HttpClientRequest> handler) {
        request.pushHandler(handler);
        return this;
    }

    @Override
    public boolean reset() {
        return request.reset();
    }

    @Override
    public boolean reset(long l) {
        return request.reset(l);
    }

    @Override
    public HttpConnection connection() {
        return request.connection();
    }

    @Override
    public ElasticTranscoderClientRequest connectionHandler(Handler<HttpConnection> handler) {
        request.connectionHandler(handler);
        return this;
    }

    @Override
    public ElasticTranscoderClientRequest writeCustomFrame(int i, int i1, Buffer buffer) {
        request.writeCustomFrame(i, i1, buffer);
        return this;
    }

    @Override
    public int streamId() {
        return request.streamId();
    }

    @Override
    public ElasticTranscoderClientRequest writeCustomFrame(HttpFrame frame) {
        request.writeCustomFrame(frame);
        return this;
    }

    protected void initAuthenticationHeaderBeforePayload() {
        if (signPayload) {
            throw new RuntimeException("Can not stream to request with signed payload");
        }
        initAuthenticationHeader(Buffer.buffer());
    }


    protected void initAuthenticationHeader(Buffer payload) {
        if (!isAuthenticated()) {
            return;
        }
        if (authenticationHeaderSet) {
            return;
        }

        authenticationHeaderSet = true;

        try {
            final String decodedQueryString = request.query() != null ? URLDecoder.decode(request.query(), "UTF-8") : null;
            final AWS4SignatureBuilder signatureBuilder = AWS4SignatureBuilder
                    .builder(ZonedDateTime.now(clock), region, serviceName)
                    .httpRequestMethod(method)
                    .canonicalUri(request.path())
                    .canonicalQueryString(decodedQueryString)
                    .awsSecretKey(awsSecretKey);

            headers().set(Headers.X_AMZ_DATE, signatureBuilder.makeSignatureFormattedDate());

            for (Map.Entry<String, String> entry : headers()) {
                signatureBuilder.header(entry.getKey(), entry.getValue());
            }

            if (signPayload) {
                signatureBuilder.payload(payload.getBytes());
                headers().set(Headers.X_AMZ_CONTENT_SHA256, signatureBuilder.getPayloadHash());
            } else {
                headers().set(Headers.X_AMZ_CONTENT_SHA256, AWS4SignatureBuilder.UNSIGNED_PAYLOAD);
            }

            log.info("S3 toSign:\n{}", signatureBuilder.makeCanonicalRequest());

            headers().set("Authorization", signatureBuilder.buildAuthorizationHeaderValue(awsAccessKey));
        } catch (UnsupportedEncodingException e) {
            log.error("Fatal error. This should never happen since the encoding is hard coded", e);
        } catch (Exception e) {
            // This will totally fail,
            // but downstream users can handle it
            log.error("Failed to sign S3 request due to " + e.getMessage(), e);
        }
    }

    private void logBody(Buffer body) {
        if (log.isDebugEnabled()) {
            log.debug("Request Body: {}", new String(body.getBytes(), Charsets.UTF_8));
        }
    }

    private boolean isAuthenticated() {
        return awsAccessKey != null && awsSecretKey != null;
    }

    private void setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
    }

    private String getMethod() {
        return method;
    }
}
