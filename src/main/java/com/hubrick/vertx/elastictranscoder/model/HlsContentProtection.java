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

import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HlsContentProtection {

    @JsonProperty("Method")
    private HlsContentProtectionMethod method;

    @JsonProperty("Key")
    private String key;

    @JsonProperty("KeyMd5")
    private String keyMd5;

    @JsonProperty("InitializationVector")
    private String initializationVector;

    @JsonProperty("LicenseAcquisitionUrl")
    private URL licenseAcquisitionUrl;

    @JsonProperty("KeyStoragePolicy")
    private KeyStoragePolicy keyStoragePolicy;

    public HlsContentProtection(HlsContentProtectionMethod method, URL licenseAcquisitionUrl, KeyStoragePolicy keyStoragePolicy) {
        checkNotNull(method, "method must not be null");
        checkNotNull(licenseAcquisitionUrl, "licenseAcquisitionUrl must not be null");
        checkNotNull(keyStoragePolicy, "keyStoragePolicy must not be null");

        this.method = method;
        this.licenseAcquisitionUrl = licenseAcquisitionUrl;
        this.keyStoragePolicy = keyStoragePolicy;
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
