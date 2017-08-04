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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AudioCodecOptions {

    @JsonProperty("Profile")
    private AudioCodecProfile profile;

    @JsonProperty("BitDepth")
    private BitDepth bitDepth;

    @JsonProperty("Signed")
    private Signed signed;

    @JsonProperty("BitOrder")
    private BitOrder bitOrder;

    public AudioCodecOptions(AudioCodecProfile profile) {
        checkNotNull(profile, "profile must not be null");

        this.profile = profile;
    }

    public void setBitDepth(BitDepth bitDepth) {
        this.bitDepth = bitDepth;
    }

    public void setSigned(Signed signed) {
        this.signed = signed;
    }

    public void setBitOrder(BitOrder bitOrder) {
        this.bitOrder = bitOrder;
    }
}
