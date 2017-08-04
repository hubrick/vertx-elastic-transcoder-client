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
package com.hubrick.vertx.elastictranscoder.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
public abstract class AbstractPipeline {

    @JsonProperty("Name")
    protected String name;

    @JsonProperty("InputBucket")
    protected String inputBucket;

    @JsonProperty("OutputBucket")
    protected String outputBucket;

    @JsonProperty("Role")
    protected String role;

    @JsonProperty("AwsKmsKeyArn")
    protected String awsKmsKeyArn;

    @JsonProperty("Notifications")
    protected Notifications notifications;

    @JsonProperty("ContentConfig")
    protected ContentConfig contentConfig;

    @JsonProperty("ThumbnailConfig")
    protected ThumbnailConfig thumbnailConfig;
}
