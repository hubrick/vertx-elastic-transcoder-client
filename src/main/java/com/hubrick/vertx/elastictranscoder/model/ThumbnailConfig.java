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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThumbnailConfig {

    @JsonProperty("Bucket")
    private String bucket;

    @JsonProperty("Permissions")
    private List<Permission> permissions;

    @JsonProperty("StorageClass")
    private StorageClass storageClass;

    public ThumbnailConfig(String bucket, StorageClass storageClass) {
        checkNotNull(StringUtils.trimToNull(bucket), "bucket must not be empty");
        checkNotNull(storageClass, "storageClass must not be null");

        this.bucket = bucket;
        this.storageClass = storageClass;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
