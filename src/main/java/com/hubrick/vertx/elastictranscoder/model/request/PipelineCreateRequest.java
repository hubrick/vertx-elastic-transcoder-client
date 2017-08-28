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
package com.hubrick.vertx.elastictranscoder.model.request;

import com.hubrick.vertx.elastictranscoder.model.AbstractPipeline;
import com.hubrick.vertx.elastictranscoder.model.ContentConfig;
import com.hubrick.vertx.elastictranscoder.model.Notifications;
import com.hubrick.vertx.elastictranscoder.model.ThumbnailConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PipelineCreateRequest extends AbstractPipeline {

    public PipelineCreateRequest(String name, String inputBucket, String outputBucket, String role, ContentConfig contentConfig, ThumbnailConfig thumbnailConfig) {
        checkNotNull(StringUtils.trimToNull(name), "name must not be empty");
        checkNotNull(StringUtils.trimToNull(inputBucket), "inputBucket must not be empty");
        checkNotNull(StringUtils.trimToNull(outputBucket), "outputBucket must not be empty");
        checkNotNull(StringUtils.trimToNull(role), "role must not be empty");
        checkNotNull(contentConfig, "contentConfig must not be null");
        checkNotNull(thumbnailConfig, "thumbnailConfig must not be null");

        this.name = name;
        this.inputBucket = inputBucket;
        this.outputBucket = outputBucket;
        this.role = role;
        this.contentConfig = contentConfig;
        this.thumbnailConfig = thumbnailConfig;
    }

    public void setAwsKmsKeyArn(String awsKmsKeyArn) {
        this.awsKmsKeyArn = awsKmsKeyArn;
    }

    public void setNotifications(Notifications notifications) {
        this.notifications = notifications;
    }
}
