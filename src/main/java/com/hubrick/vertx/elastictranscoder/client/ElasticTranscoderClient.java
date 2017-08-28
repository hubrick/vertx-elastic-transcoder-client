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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.hubrick.vertx.elastictranscoder.exception.HttpErrorException;
import com.hubrick.vertx.elastictranscoder.model.HeaderOnlyResponse;
import com.hubrick.vertx.elastictranscoder.model.JobStatus;
import com.hubrick.vertx.elastictranscoder.model.Response;
import com.hubrick.vertx.elastictranscoder.model.ResponseWithBody;
import com.hubrick.vertx.elastictranscoder.model.header.CommonResponseHeaders;
import com.hubrick.vertx.elastictranscoder.model.request.JobCreateRequest;
import com.hubrick.vertx.elastictranscoder.model.request.JobsListRequest;
import com.hubrick.vertx.elastictranscoder.model.request.PipelineCreateRequest;
import com.hubrick.vertx.elastictranscoder.model.request.PipelinesListRequest;
import com.hubrick.vertx.elastictranscoder.model.request.PresetCreateRequest;
import com.hubrick.vertx.elastictranscoder.model.request.PresetsListRequest;
import com.hubrick.vertx.elastictranscoder.model.response.ErrorResponse;
import com.hubrick.vertx.elastictranscoder.model.response.JobCancelResponse;
import com.hubrick.vertx.elastictranscoder.model.response.JobCreateResponse;
import com.hubrick.vertx.elastictranscoder.model.response.JobReadResponse;
import com.hubrick.vertx.elastictranscoder.model.response.JobsListResponse;
import com.hubrick.vertx.elastictranscoder.model.response.PipelineCreateResponse;
import com.hubrick.vertx.elastictranscoder.model.response.PipelineDeleteResponse;
import com.hubrick.vertx.elastictranscoder.model.response.PipelineReadResponse;
import com.hubrick.vertx.elastictranscoder.model.response.PipelinesListResponse;
import com.hubrick.vertx.elastictranscoder.model.response.PresetCreateResponse;
import com.hubrick.vertx.elastictranscoder.model.response.PresetDeleteResponse;
import com.hubrick.vertx.elastictranscoder.model.response.PresetReadResponse;
import com.hubrick.vertx.elastictranscoder.model.response.PresetsListResponse;
import com.hubrick.vertx.elastictranscoder.util.UrlEncodingUtils;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ElasticTranscoderClient {

    private static final Logger log = LoggerFactory.getLogger(ElasticTranscoderClient.class);
    private static final String DEFAULT_REGION = "us-east-1";
    private static final String DEFAULT_ENDPOINT = "elastictranscoder.amazonaws.com";
    private static final String ENDPOINT_PATTERN = "elastictranscoder.{0}.amazonaws.com";

    private final ObjectMapper objectMapper;
    private final Long globalTimeout;
    private final String awsRegion;

    private final String hostname;

    private final Clock clock;
    private final HttpClient client;
    private final String awsAccessKey;
    private final String awsSecretKey;
    private final String awsServiceName;
    private final boolean signPayload = true;

    public ElasticTranscoderClient(Vertx vertx, ElasticTranscoderClientOptions elasticTranscoderClientOptions) {
        this(vertx, elasticTranscoderClientOptions, Clock.systemUTC());
    }

    public ElasticTranscoderClient(Vertx vertx, ElasticTranscoderClientOptions elasticTranscoderClientOptions, Clock clock) {
        checkNotNull(vertx, "vertx must not be null");
        checkNotNull(isNotBlank(elasticTranscoderClientOptions.getAwsRegion()), "AWS region must be set");
        checkNotNull(isNotBlank(elasticTranscoderClientOptions.getAwsServiceName()), "AWS service name must be set");
        checkNotNull(clock, "Clock must not be null");
        checkNotNull(elasticTranscoderClientOptions.getGlobalTimeoutMs(), "global timeout must be null");
        checkArgument(elasticTranscoderClientOptions.getGlobalTimeoutMs() > 0, "global timeout must be more than zero ms");

        this.objectMapper = createObjectMapper();

        this.clock = clock;
        this.awsServiceName = elasticTranscoderClientOptions.getAwsServiceName();
        this.awsRegion = elasticTranscoderClientOptions.getAwsRegion();
        this.awsAccessKey = elasticTranscoderClientOptions.getAwsAccessKey();
        this.awsSecretKey = elasticTranscoderClientOptions.getAwsSecretKey();
        this.globalTimeout = elasticTranscoderClientOptions.getGlobalTimeoutMs();

        final String hostnameOverride = elasticTranscoderClientOptions.getHostnameOverride();
        if (!Strings.isNullOrEmpty(hostnameOverride)) {
            hostname = hostnameOverride;
        } else {
            if (DEFAULT_REGION.equals(elasticTranscoderClientOptions.getAwsRegion())) {
                hostname = DEFAULT_ENDPOINT;
            } else {
                hostname = MessageFormat.format(ENDPOINT_PATTERN, awsRegion);
            }
        }

        final ElasticTranscoderClientOptions options = new ElasticTranscoderClientOptions(elasticTranscoderClientOptions);
        options.setDefaultHost(hostname);

        this.client = vertx.createHttpClient(options);
    }

    public String getAwsRegion() {
        return awsRegion;
    }

    public String getAwsServiceName() {
        return awsServiceName;
    }

    public String getHostname() {
        return hostname;
    }

    public void close() {
        client.close();
    }

    public Long getGlobalTimeout() {
        return globalTimeout;
    }

    public void createPipeline(PipelineCreateRequest pipelineCreateRequest,
                               Handler<Response<CommonResponseHeaders, PipelineCreateResponse>> handler,
                               Handler<Throwable> exceptionHandler) {
        checkNotNull(pipelineCreateRequest, "pipelineCreateRequest must not be null");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createPipelineCreateRequest(
                    pipelineCreateRequest,
                    new JsonBodyResponseHandler("createPipeline", objectMapper, PipelineCreateResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.putHeader(Headers.CONTENT_TYPE, "application/json");
            request.end(Buffer.buffer(objectMapper.writeValueAsBytes(pipelineCreateRequest)));
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void listPipelines(PipelinesListRequest pipelineListRequest,
                              Handler<Response<CommonResponseHeaders, PipelinesListResponse>> handler,
                              Handler<Throwable> exceptionHandler) {
        checkNotNull(pipelineListRequest, "pipelineListRequest must not be null");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createPipelinesListRequest(
                    pipelineListRequest,
                    new JsonBodyResponseHandler("listPipelines", objectMapper, PipelinesListResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.end();
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void readPipeline(String pipelineId,
                             Handler<Response<CommonResponseHeaders, PipelineReadResponse>> handler,
                             Handler<Throwable> exceptionHandler) {
        checkNotNull(StringUtils.trimToNull(pipelineId), "pipelineId must not be empty");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createPipelineReadRequest(
                    pipelineId,
                    new JsonBodyResponseHandler("readPipeline", objectMapper, PipelineReadResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.end();
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void deletePipeline(String pipelineId,
                               Handler<Response<CommonResponseHeaders, PipelineDeleteResponse>> handler,
                               Handler<Throwable> exceptionHandler) {
        checkNotNull(StringUtils.trimToNull(pipelineId), "pipelineId must not be empty");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = deletePipelineReadRequest(
                    pipelineId,
                    new JsonBodyResponseHandler("deletePipeline", objectMapper, PipelineReadResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.end();
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void createJob(JobCreateRequest jobCreateRequest,
                          Handler<Response<CommonResponseHeaders, JobCreateResponse>> handler,
                          Handler<Throwable> exceptionHandler) {
        checkNotNull(jobCreateRequest, "jobCreateRequest must not be null");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createJobCreateRequest(
                    jobCreateRequest,
                    new JsonBodyResponseHandler("createJob", objectMapper, JobCreateResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.putHeader(Headers.CONTENT_TYPE, "application/json");
            request.end(Buffer.buffer(objectMapper.writeValueAsBytes(jobCreateRequest)));
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void listJobsByPipeline(String pipelineId,
                                   JobsListRequest jobsListRequest,
                                   Handler<Response<CommonResponseHeaders, JobsListResponse>> handler,
                                   Handler<Throwable> exceptionHandler) {
        checkNotNull(StringUtils.trimToNull(pipelineId), "pipelineId must not be empty");
        checkNotNull(jobsListRequest, "jobsListRequest must not be null");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createJobsListByPipelineRequest(
                    pipelineId,
                    jobsListRequest,
                    new JsonBodyResponseHandler("listJobsByPipeline", objectMapper, JobsListResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.end();
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void listJobsByStatus(JobStatus status,
                                 JobsListRequest jobsListRequest,
                                 Handler<Response<CommonResponseHeaders, JobsListResponse>> handler,
                                 Handler<Throwable> exceptionHandler) {
        checkNotNull(status, "status must not be null");
        checkNotNull(jobsListRequest, "jobsListRequest must not be null");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createJobsListByStatusRequest(
                    status,
                    jobsListRequest,
                    new JsonBodyResponseHandler("listJobsByStatus", objectMapper, JobsListResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.end();
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void readJob(String jobId,
                        Handler<Response<CommonResponseHeaders, JobReadResponse>> handler,
                        Handler<Throwable> exceptionHandler) {
        checkNotNull(StringUtils.trimToNull(jobId), "jobId must not be empty");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createJobReadRequest(
                    jobId,
                    new JsonBodyResponseHandler("readJob", objectMapper, JobReadResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.end();
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void cancelJob(String jobId,
                          Handler<Response<CommonResponseHeaders, JobCancelResponse>> handler,
                          Handler<Throwable> exceptionHandler) {
        checkNotNull(StringUtils.trimToNull(jobId), "jobId must not be empty");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createCancelJobRequest(
                    jobId,
                    new JsonBodyResponseHandler("cancelJob", objectMapper, JobCancelResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.end();
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void createPreset(PresetCreateRequest presetCreateRequest,
                             Handler<Response<CommonResponseHeaders, PresetCreateResponse>> handler,
                             Handler<Throwable> exceptionHandler) {
        checkNotNull(presetCreateRequest, "presetCreateRequest must not be null");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createPresetCreateRequest(
                    presetCreateRequest,
                    new JsonBodyResponseHandler("createPreset", objectMapper, PresetCreateResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.putHeader(Headers.CONTENT_TYPE, "application/json");
            request.end(Buffer.buffer(objectMapper.writeValueAsBytes(presetCreateRequest)));
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void listPreset(PresetsListRequest presetsListRequest,
                           Handler<Response<CommonResponseHeaders, PresetsListResponse>> handler,
                           Handler<Throwable> exceptionHandler) {
        checkNotNull(presetsListRequest, "presetsListRequest must not be null");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createPresetsListRequest(
                    presetsListRequest,
                    new JsonBodyResponseHandler("listPreset", objectMapper, PresetCreateResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.putHeader(Headers.CONTENT_TYPE, "application/json");
            request.end(Buffer.buffer(objectMapper.writeValueAsBytes(presetsListRequest)));
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void readPreset(String presetId,
                           Handler<Response<CommonResponseHeaders, PresetReadResponse>> handler,
                           Handler<Throwable> exceptionHandler) {
        checkNotNull(StringUtils.trimToNull(presetId), "presetId must not be empty");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createPresetReadRequest(
                    presetId,
                    new JsonBodyResponseHandler("readPreset", objectMapper, PresetReadResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.end();
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    public void deletePreset(String presetId,
                             Handler<Response<CommonResponseHeaders, JobCancelResponse>> handler,
                             Handler<Throwable> exceptionHandler) {
        checkNotNull(StringUtils.trimToNull(presetId), "presetId must not be empty");
        checkNotNull(handler, "handler must not be null");
        checkNotNull(exceptionHandler, "exceptionHandler must not be null");

        try {
            final ElasticTranscoderClientRequest request = createDeletePresetRequest(
                    presetId,
                    new JsonBodyResponseHandler("deletePreset", objectMapper, PresetDeleteResponse.class, new CommonResponseHeadersMapper(), handler, exceptionHandler)
            );
            request.exceptionHandler(exceptionHandler);
            request.end();
        } catch (Throwable t) {
            exceptionHandler.handle(t);
        }
    }

    private ElasticTranscoderClientRequest createPipelineCreateRequest(PipelineCreateRequest pipelineCreateRequest, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.post("/2012-09-25/pipelines", handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "POST",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private ElasticTranscoderClientRequest createPipelinesListRequest(PipelinesListRequest pipelinesListRequest, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.get(UrlEncodingUtils.addParamsSortedToUrl("/2012-09-25/pipelines", populatePipelineListQueryParams(pipelinesListRequest)), handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "GET",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private Map<String, String> populatePipelineListQueryParams(PipelinesListRequest pipelineListRequest) {
        final Map<String, String> queryParams = new HashMap<>();

        queryParams.put("Ascending", pipelineListRequest.getAscending().toString());
        queryParams.put("PageToken", StringUtils.trim(pipelineListRequest.getPageToken()));

        return queryParams;
    }

    private ElasticTranscoderClientRequest createPipelineReadRequest(String pipelineId, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.get("/2012-09-25/pipelines/" + pipelineId, handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "GET",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private ElasticTranscoderClientRequest deletePipelineReadRequest(String pipelineId, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.delete("/2012-09-25/pipelines/" + pipelineId, handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "DELETE",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private ElasticTranscoderClientRequest createJobCreateRequest(JobCreateRequest jobCreateRequest, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.post("/2012-09-25/jobs", handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "POST",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private ElasticTranscoderClientRequest createJobsListByPipelineRequest(String pipelineId, JobsListRequest jobsListRequest, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.get(UrlEncodingUtils.addParamsSortedToUrl("/2012-09-25/jobsByPipeline/" + pipelineId, populateJobsListQueryParams(jobsListRequest)), handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "GET",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private ElasticTranscoderClientRequest createJobsListByStatusRequest(JobStatus status, JobsListRequest jobsListRequest, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.get(UrlEncodingUtils.addParamsSortedToUrl("/2012-09-25/jobsByStatus/" + status.toString(), populateJobsListQueryParams(jobsListRequest)), handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "GET",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private Map<String, String> populateJobsListQueryParams(JobsListRequest jobsListRequest) {
        final Map<String, String> queryParams = new HashMap<>();

        queryParams.put("Ascending", jobsListRequest.getAscending().toString());
        queryParams.put("PageToken", StringUtils.trim(jobsListRequest.getPageToken()));

        return queryParams;
    }

    private ElasticTranscoderClientRequest createJobReadRequest(String jobId, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.get("/2012-09-25/jobs/" + jobId, handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "GET",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private ElasticTranscoderClientRequest createCancelJobRequest(String jobId, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.delete("/2012-09-25/jobs/" + jobId, handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "DELETE",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private ElasticTranscoderClientRequest createPresetCreateRequest(PresetCreateRequest presetCreateRequest, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.post("/2012-09-25/presets", handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "POST",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private ElasticTranscoderClientRequest createPresetsListRequest(PresetsListRequest presetsListRequest, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.get(UrlEncodingUtils.addParamsSortedToUrl("/2012-09-25/presets", populatePresetsListQueryParams(presetsListRequest)), handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "GET",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private Map<String, String> populatePresetsListQueryParams(PresetsListRequest presetsListRequest) {
        final Map<String, String> queryParams = new HashMap<>();

        queryParams.put("Ascending", presetsListRequest.getAscending().toString());
        queryParams.put("PageToken", StringUtils.trim(presetsListRequest.getPageToken()));

        return queryParams;
    }

    private ElasticTranscoderClientRequest createPresetReadRequest(String presetId, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.get("/2012-09-25/presets/" + presetId, handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "GET",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }


    private ElasticTranscoderClientRequest createDeletePresetRequest(String presetId, Handler<HttpClientResponse> handler) {
        final HttpClientRequest httpRequest = client.delete("/2012-09-25/presets/" + presetId, handler);
        final ElasticTranscoderClientRequest elasticTranscoderClientRequest = new ElasticTranscoderClientRequest(
                "DELETE",
                awsRegion,
                awsServiceName,
                httpRequest,
                awsAccessKey,
                awsSecretKey,
                clock,
                signPayload
        )
                .setTimeout(globalTimeout)
                .putHeader(Headers.HOST, hostname);

        return elasticTranscoderClientRequest;
    }

    private class JsonBodyResponseHandler<H extends CommonResponseHeaders, B> implements Handler<HttpClientResponse> {

        private final String action;
        private final ObjectMapper objectMapper;
        private final Class<B> clazz;
        private final ResponseHeaderMapper<H> responseHeaderMapper;
        private final Handler<Response<H, B>> successHandler;
        private final Handler<Throwable> exceptionHandler;

        private JsonBodyResponseHandler(String action, ObjectMapper objectMapper, Class<B> clazz, ResponseHeaderMapper<H> responseHeaderMapper, Handler<Response<H, B>> successHandler, Handler<Throwable> exceptionHandler) {
            this.action = action;
            this.objectMapper = objectMapper;
            this.clazz = clazz;
            this.responseHeaderMapper = responseHeaderMapper;
            this.successHandler = successHandler;
            this.exceptionHandler = exceptionHandler;
        }

        @Override
        public void handle(HttpClientResponse event) {
            event.bodyHandler(buffer -> {
                try {
                    if (event.statusCode() / 100 != 2) {
                        log.warn("Error occurred. Status: {}, Message: {}", event.statusCode(), event.statusMessage());
                        if (log.isWarnEnabled()) {
                            log.warn("Response: {}", new String(buffer.getBytes(), Charsets.UTF_8));
                        }

                        exceptionHandler.handle(
                                new HttpErrorException(
                                        event.statusCode(),
                                        event.statusMessage(),
                                        objectMapper.readValue(buffer.getBytes(), ErrorResponse.class),
                                        "Error occurred on '" + action + "'"
                                )
                        );
                    } else {
                        log.info("Request successful. Status: {}, Message: {}", event.statusCode(), event.statusMessage());
                        if (log.isDebugEnabled()) {
                            log.debug("Response: {}", new String(buffer.getBytes(), Charsets.UTF_8));
                        }
                        successHandler.handle(new ResponseWithBody<>(responseHeaderMapper.map(event.headers()), objectMapper.readValue(buffer.getBytes(), clazz)));
                    }
                } catch (JsonParseException | JsonMappingException e) {
                    final String response = new String(buffer.getBytes(), Charsets.UTF_8);
                    exceptionHandler.handle(
                            new com.hubrick.vertx.elastictranscoder.exception.UnmarshalException(
                                    response,
                                    "Error unmarshalling response: '" + response + "'",
                                    e
                            )
                    );
                } catch (Exception e) {
                    exceptionHandler.handle(e);
                }
            });
        }
    }

    private class HeadersResponseHandler<H extends CommonResponseHeaders> implements Handler<HttpClientResponse> {

        private final String action;
        private final ObjectMapper objectMapper;
        private final ResponseHeaderMapper<H> responseHeaderMapper;
        private final Handler<Response<H, Void>> successHandler;
        private final Handler<Throwable> exceptionHandler;

        private HeadersResponseHandler(String action, ObjectMapper objectMapper, ResponseHeaderMapper<H> responseHeaderMapper, Handler<Response<H, Void>> successHandler, Handler<Throwable> exceptionHandler) {
            this.action = action;
            this.objectMapper = objectMapper;
            this.responseHeaderMapper = responseHeaderMapper;
            this.successHandler = successHandler;
            this.exceptionHandler = exceptionHandler;
        }

        @Override
        public void handle(HttpClientResponse event) {
            event.bodyHandler(buffer -> {
                try {
                    if (event.statusCode() / 100 != 2) {
                        log.warn("Error occurred. Status: {}, Message: {}", event.statusCode(), event.statusMessage());
                        if (log.isWarnEnabled()) {
                            log.warn("Response: {}", new String(buffer.getBytes(), Charsets.UTF_8));
                        }

                        exceptionHandler.handle(
                                new HttpErrorException(
                                        event.statusCode(),
                                        event.statusMessage(),
                                        objectMapper.readValue(buffer.getBytes(), ErrorResponse.class),
                                        "Error occurred on '" + action + "'"
                                )
                        );
                    } else {
                        log.info("Request successful. Status: {}, Message: {}", event.statusCode(), event.statusMessage());
                        if (log.isDebugEnabled()) {
                            log.debug("Response: {}", new String(buffer.getBytes(), Charsets.UTF_8));
                        }
                        successHandler.handle(new HeaderOnlyResponse<>(responseHeaderMapper.map(event.headers())));
                    }
                } catch (Exception e) {
                    exceptionHandler.handle(e);
                }
            });
        }
    }

    private class CommonResponseHeadersMapper implements ResponseHeaderMapper<CommonResponseHeaders> {

        @Override
        public CommonResponseHeaders map(MultiMap headers) {
            final CommonResponseHeaders commonResponseHeaders = new CommonResponseHeaders();
            populateCommonResponseHeaders(headers, commonResponseHeaders);
            return commonResponseHeaders;
        }
    }

    private void populateCommonResponseHeaders(MultiMap headers, CommonResponseHeaders commonResponseHeaders) {
        commonResponseHeaders.setContentLength(Optional.ofNullable(headers.get(Headers.CONTENT_LENGTH)).filter(StringUtils::isNotBlank).map(Long::valueOf).orElse(null));
        commonResponseHeaders.setDate(Optional.ofNullable(headers.get(Headers.DATE)).filter(StringUtils::isNotBlank).orElse(null));
        commonResponseHeaders.setAmzRequestId(Optional.ofNullable(headers.get(Headers.X_AMZ_REQUEST_ID)).filter(StringUtils::isNotBlank).orElse(null));
    }

    private interface ResponseHeaderMapper<T extends CommonResponseHeaders> {
        T map(MultiMap headers);
    }

    private ObjectMapper createObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper;
    }
}
