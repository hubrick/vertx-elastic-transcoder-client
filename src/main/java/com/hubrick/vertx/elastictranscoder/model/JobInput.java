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
public class JobInput {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Encryption")
    private Encryption encryption;

    @JsonProperty("TimeSpan")
    private TimeSpan timeSpan;

    @JsonProperty("FrameRate")
    private FrameRate frameRate;

    @JsonProperty("Resolution")
    private Resolution resolution;

    @JsonProperty("AspectRatio")
    private AspectRatio aspectRatio;

    @JsonProperty("Interlaced")
    private Interlaced interlaced;

    @JsonProperty("Container")
    private JobContainer container;

    @JsonProperty("InputCaptions")
    private InputCaptions inputCaptions;

    public JobInput(String key) {
        checkNotNull(StringUtils.trimToNull(key), "key must not be null");

        this.key = key;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        this.timeSpan = timeSpan;
    }

    public void setFrameRate(FrameRate frameRate) {
        this.frameRate = frameRate;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public void setAspectRatio(AspectRatio aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public void setInterlaced(Interlaced interlaced) {
        this.interlaced = interlaced;
    }

    public void setContainer(JobContainer container) {
        this.container = container;
    }

    public void setInputCaptions(InputCaptions inputCaptions) {
        this.inputCaptions = inputCaptions;
    }
}
