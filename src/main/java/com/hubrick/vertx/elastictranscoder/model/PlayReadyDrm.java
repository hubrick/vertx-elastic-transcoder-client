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

import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayReadyDrm {

    @JsonProperty("Format")
    private PlayReadyDrmFormat format;

    @JsonProperty("Key")
    private String key;

    @JsonProperty("KeyId")
    private String keyId;

    @JsonProperty("KeyMd5")
    private String keyMd5;

    @JsonProperty("InitializationVector")
    private String initializationVector;

    @JsonProperty("LicenseAcquisitionUrl")
    private URL licenseAcquisitionUrl;

    public PlayReadyDrm(PlayReadyDrmFormat format, String key, String keyId, String keyMd5, URL licenseAcquisitionUrl) {
        checkNotNull(format, "format must not be null");
        checkNotNull(StringUtils.trimToNull(key), "key must not be empty");
        checkNotNull(StringUtils.trimToNull(keyId), "keyId must not be null");
        checkNotNull(StringUtils.trimToNull(keyMd5), "keyMd5 must not be null");
        checkNotNull(licenseAcquisitionUrl, "licenseAcquisitionUrl must not be null");

        this.format = format;
        this.key = key;
        this.keyId = keyId;
        this.keyMd5 = keyMd5;
        this.licenseAcquisitionUrl = licenseAcquisitionUrl;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setKeyMd5(String keyMd5) {
        this.keyMd5 = keyMd5;
    }

    public void setInitializationVector(String initializationVector) {
        this.initializationVector = initializationVector;
    }
}
