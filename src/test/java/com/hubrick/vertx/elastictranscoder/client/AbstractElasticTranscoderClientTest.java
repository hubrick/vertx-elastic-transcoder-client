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

import com.google.common.io.Resources;
import com.hubrick.vertx.elastictranscoder.AbstractFunctionalTest;
import com.hubrick.vertx.elastictranscoder.S3TestCredentials;
import org.junit.Before;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
public abstract class AbstractElasticTranscoderClientTest extends AbstractFunctionalTest {

    public static final String HOSTNAME = "localhost";

    private ElasticTranscoderClient s3Client;

    @Before
    public void setUp() throws Exception {
        final ElasticTranscoderClientOptions elasticTranscoderClientOptions = new ElasticTranscoderClientOptions();
        elasticTranscoderClientOptions.setDefaultHost(HOSTNAME);
        elasticTranscoderClientOptions.setDefaultPort(MOCKSERVER_PORT);
        elasticTranscoderClientOptions.setMaxPoolSize(10);
        elasticTranscoderClientOptions.setAwsRegion(S3TestCredentials.REGION);
        elasticTranscoderClientOptions.setAwsServiceName(S3TestCredentials.SERVICE_NAME);
        elasticTranscoderClientOptions.setHostnameOverride(HOSTNAME);

        augmentClientOptions(elasticTranscoderClientOptions);

        s3Client = new ElasticTranscoderClient(
                vertx,
                elasticTranscoderClientOptions,
                Clock.fixed(Instant.ofEpochSecond(1478782934), ZoneId.of("UTC")));

    }

    protected abstract void augmentClientOptions(final ElasticTranscoderClientOptions clientOptions);

    void addCredentials(final ElasticTranscoderClientOptions clientOptions) {
        clientOptions.setAwsAccessKey(S3TestCredentials.AWS_ACCESS_KEY);
        clientOptions.setAwsSecretKey(S3TestCredentials.AWS_SECRET_KEY);
    }

    void mockGetObject(Header... expectedHeaders) throws IOException {
        mock(
                Collections.emptyMap(),
                "GET",
                "/bucket/key",
                200,
                "response".getBytes(),
                expectedHeaders
        );
    }

    void mockHeadObject(Header... expectedHeaders) throws IOException {
        mock(
                Collections.emptyMap(),
                "HEAD",
                "/bucket/key",
                200,
                "0".getBytes(),
                expectedHeaders
        );
    }

    void mockGetObjectErrorResponse(Header... expectedHeaders) throws IOException {
        mock(
                Collections.emptyMap(),
                "GET",
                "/bucket/key",
                403,
                Resources.toByteArray(Resources.getResource(AbstractElasticTranscoderClientTest.class, "/response/errorResponse.xml")),
                expectedHeaders
        );
    }

    void mock(Map<String, List<String>> expectedQueryParams, String method, String path, Integer statusCode, byte[] responseBody, Header... expectedHeaders) throws IOException {
        mock(expectedQueryParams, method, path, statusCode, null, responseBody, Collections.emptyList(), expectedHeaders);
    }

    void mock(Map<String, List<String>> expectedQueryParams, String method, String path, Integer statusCode, byte[] responseBody, List<Header> responseHeaders, Header... expectedHeaders) throws IOException {
        mock(expectedQueryParams, method, path, statusCode, null, responseBody, responseHeaders, expectedHeaders);
    }

    void mock(Map<String, List<String>> expectedQueryParams, String method, String path, Integer statusCode, byte[] requestBody, byte[] responseBody, List<Header> responseHeaders, Header... expectedHeaders) throws IOException {
        final HttpRequest httpRequest = request()
                .withMethod(method)
                .withPath(path)
                .withHeaders(expectedHeaders)
                .withQueryStringParameters(expectedQueryParams);

        if (requestBody != null) {
            httpRequest.withBody(requestBody);
        }

        getMockServerClient().when(
                httpRequest
        ).respond(
                response()
                        .withStatusCode(statusCode)
                        .withHeaders(responseHeaders)
                        .withHeader(Header.header("Content-Type", "application/xml;charset=UTF-8"))
                        .withBody(responseBody)
        );
    }
}
