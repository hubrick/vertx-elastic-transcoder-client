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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CaptionSource {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Encryption")
    private Encryption encryption;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("TimeOffset")
    private String timeOffset;

    @JsonProperty("Label")
    private String label;

    public CaptionSource(String key, String language) {
        checkNotNull(StringUtils.trimToNull(key), "key must not be empty");
        checkNotNull(StringUtils.trimToNull(language), "language must not be empty");

        this.key = key;
        this.language = language;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }

    public void setTimeOffset(String timeOffset) {
        this.timeOffset = timeOffset;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
