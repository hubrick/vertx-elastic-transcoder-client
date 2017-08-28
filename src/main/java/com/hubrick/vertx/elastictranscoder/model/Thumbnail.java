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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubrick.vertx.elastictranscoder.deserializer.StringToIntegerDeserializer;
import com.hubrick.vertx.elastictranscoder.serializer.IntegerToStringSerializer;
import com.hubrick.vertx.elastictranscoder.serializer.NullToAutoSerializer;
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
public class Thumbnail {

    @JsonProperty("Format")
    private ImageFormat format;

    @JsonProperty("Interval")
    @JsonSerialize(using = IntegerToStringSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer interval;

    @JsonProperty("MaxWidth")
    @JsonSerialize(using = IntegerToStringSerializer.class, nullsUsing = NullToAutoSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer maxWidth;

    @JsonProperty("MaxHeight")
    @JsonSerialize(using = IntegerToStringSerializer.class, nullsUsing = NullToAutoSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer maxHeight;

    @JsonProperty("SizingPolicy")
    private SizingPolicy sizingPolicy;

    @JsonProperty("PaddingPolicy")
    private PaddingPolicy paddingPolicy;

    @JsonProperty("Resolution")
    private String resolution;

    @JsonProperty("AspectRatio")
    private AspectRatio aspectRatio;

    public Thumbnail(ImageFormat format,
                     Integer interval,
                     SizingPolicy sizingPolicy,
                     PaddingPolicy paddingPolicy) {
        checkNotNull(format, "format must not be null");
        checkNotNull(interval, "interval must not be null");
        checkNotNull(sizingPolicy, "sizingPolicy must not be null");
        checkNotNull(paddingPolicy, "paddingPolicy must not be null");

        this.format = format;
        this.interval = interval;
        this.sizingPolicy = sizingPolicy;
        this.paddingPolicy = paddingPolicy;
    }

    public Thumbnail(ImageFormat format,
                     Integer interval,
                     AspectRatio aspectRatio) {
        checkNotNull(format, "format must not be null");
        checkNotNull(interval, "interval must not be null");
        checkNotNull(aspectRatio, "aspectRatio must not be null");

        this.format = format;
        this.interval = interval;
        this.aspectRatio = aspectRatio;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
