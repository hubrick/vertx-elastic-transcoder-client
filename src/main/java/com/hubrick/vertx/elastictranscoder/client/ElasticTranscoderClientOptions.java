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

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.Http2Settings;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.net.JdkSSLEngineOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.KeyCertOptions;
import io.vertx.core.net.OpenSSLEngineOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.core.net.PemTrustOptions;
import io.vertx.core.net.PfxOptions;
import io.vertx.core.net.ProxyOptions;
import io.vertx.core.net.SSLEngineOptions;
import io.vertx.core.net.TrustOptions;

import java.util.List;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
public class ElasticTranscoderClientOptions extends HttpClientOptions {

    private String awsAccessKey;
    private String awsSecretKey;
    private String awsRegion;
    private String awsServiceName;
    private Long globalTimeoutMs = 10000L;
    private String hostnameOverride;

    public ElasticTranscoderClientOptions() {
        super();
        super.setSsl(true);
        super.setDefaultPort(443);
    }

    ElasticTranscoderClientOptions(final ElasticTranscoderClientOptions other) {
        super(other);

        setAwsAccessKey(other.getAwsAccessKey());
        setAwsSecretKey(other.getAwsSecretKey());
        setAwsRegion(other.getAwsRegion());
        setAwsServiceName(other.getAwsServiceName());
        setGlobalTimeoutMs(other.getGlobalTimeoutMs());
        setHostnameOverride(other.getHostnameOverride());
    }

    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public ElasticTranscoderClientOptions setAwsAccessKey(final String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
        return this;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public ElasticTranscoderClientOptions setAwsSecretKey(final String awsSecretKey) {
        this.awsSecretKey = awsSecretKey;
        return this;
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public ElasticTranscoderClientOptions setAwsRegion(final String awsRegion) {
        this.awsRegion = awsRegion;
        return this;
    }

    public String getAwsServiceName() {
        return awsServiceName;
    }

    public ElasticTranscoderClientOptions setAwsServiceName(final String awsServiceName) {
        this.awsServiceName = awsServiceName;
        return this;
    }

    public Long getGlobalTimeoutMs() {
        return globalTimeoutMs;
    }

    public ElasticTranscoderClientOptions setGlobalTimeoutMs(final Long globalTimeoutMs) {
        this.globalTimeoutMs = globalTimeoutMs;
        return this;
    }

    public String getHostnameOverride() {
        return hostnameOverride;
    }

    public ElasticTranscoderClientOptions setHostnameOverride(final String hostnameOverride) {
        this.hostnameOverride = hostnameOverride;
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setSendBufferSize(final int sendBufferSize) {
        super.setSendBufferSize(sendBufferSize);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setReceiveBufferSize(final int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setReuseAddress(final boolean reuseAddress) {
        super.setReuseAddress(reuseAddress);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setTrafficClass(final int trafficClass) {
        super.setTrafficClass(trafficClass);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setTcpNoDelay(final boolean tcpNoDelay) {
        super.setTcpNoDelay(tcpNoDelay);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setTcpKeepAlive(final boolean tcpKeepAlive) {
        super.setTcpKeepAlive(tcpKeepAlive);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setSoLinger(final int soLinger) {
        super.setSoLinger(soLinger);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setUsePooledBuffers(final boolean usePooledBuffers) {
        super.setUsePooledBuffers(usePooledBuffers);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setIdleTimeout(final int idleTimeout) {
        super.setIdleTimeout(idleTimeout);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setSsl(final boolean ssl) {
        super.setSsl(ssl);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setKeyCertOptions(final KeyCertOptions options) {
        super.setKeyCertOptions(options);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setKeyStoreOptions(final JksOptions options) {
        super.setKeyStoreOptions(options);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setPfxKeyCertOptions(final PfxOptions options) {
        super.setPfxKeyCertOptions(options);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setTrustOptions(final TrustOptions options) {
        super.setTrustOptions(options);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setPemKeyCertOptions(final PemKeyCertOptions options) {
        super.setPemKeyCertOptions(options);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setTrustStoreOptions(final JksOptions options) {
        super.setTrustStoreOptions(options);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setPfxTrustOptions(final PfxOptions options) {
        super.setPfxTrustOptions(options);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setPemTrustOptions(final PemTrustOptions options) {
        super.setPemTrustOptions(options);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions addEnabledCipherSuite(final String suite) {
        super.addEnabledCipherSuite(suite);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions addEnabledSecureTransportProtocol(final String protocol) {
        super.addEnabledSecureTransportProtocol(protocol);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions addCrlPath(final String crlPath) throws NullPointerException {
        super.addCrlPath(crlPath);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions addCrlValue(final Buffer crlValue) throws NullPointerException {
        super.addCrlValue(crlValue);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setConnectTimeout(final int connectTimeout) {
        super.setConnectTimeout(connectTimeout);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setTrustAll(final boolean trustAll) {
        super.setTrustAll(trustAll);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setHttp2MultiplexingLimit(final int limit) {
        super.setHttp2MultiplexingLimit(limit);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setMaxPoolSize(final int maxPoolSize) {
        super.setMaxPoolSize(maxPoolSize);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setHttp2MaxPoolSize(final int max) {
        super.setHttp2MaxPoolSize(max);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setHttp2ConnectionWindowSize(final int http2ConnectionWindowSize) {
        super.setHttp2ConnectionWindowSize(http2ConnectionWindowSize);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setKeepAlive(final boolean keepAlive) {
        super.setKeepAlive(keepAlive);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setPipelining(final boolean pipelining) {
        super.setPipelining(pipelining);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setPipeliningLimit(final int limit) {
        super.setPipeliningLimit(limit);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setVerifyHost(final boolean verifyHost) {
        super.setVerifyHost(verifyHost);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setTryUseCompression(final boolean tryUseCompression) {
        super.setTryUseCompression(tryUseCompression);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setMaxWebsocketFrameSize(final int maxWebsocketFrameSize) {
        super.setMaxWebsocketFrameSize(maxWebsocketFrameSize);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setDefaultHost(final String defaultHost) {
        super.setDefaultHost(defaultHost);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setDefaultPort(final int defaultPort) {
        super.setDefaultPort(defaultPort);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setProtocolVersion(final HttpVersion protocolVersion) {
        super.setProtocolVersion(protocolVersion);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setMaxChunkSize(final int maxChunkSize) {
        super.setMaxChunkSize(maxChunkSize);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setMaxWaitQueueSize(final int maxWaitQueueSize) {
        super.setMaxWaitQueueSize(maxWaitQueueSize);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setInitialSettings(final Http2Settings settings) {
        super.setInitialSettings(settings);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setSslEngineOptions(final SSLEngineOptions sslEngineOptions) {
        super.setSslEngineOptions(sslEngineOptions);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setJdkSslEngineOptions(final JdkSSLEngineOptions sslEngineOptions) {
        super.setJdkSslEngineOptions(sslEngineOptions);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setOpenSslEngineOptions(final OpenSSLEngineOptions sslEngineOptions) {
        super.setOpenSslEngineOptions(sslEngineOptions);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setAlpnVersions(final List<HttpVersion> alpnVersions) {
        super.setAlpnVersions(alpnVersions);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setHttp2ClearTextUpgrade(final boolean value) {
        super.setHttp2ClearTextUpgrade(value);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setMetricsName(final String metricsName) {
        super.setMetricsName(metricsName);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setProxyOptions(final ProxyOptions proxyOptions) {
        super.setProxyOptions(proxyOptions);
        return this;
    }

    @Override
    public ElasticTranscoderClientOptions setLogActivity(final boolean logEnabled) {
        super.setLogActivity(logEnabled);
        return this;
    }
}
