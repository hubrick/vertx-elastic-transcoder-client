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

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {

    @JsonProperty("Codec")
    private VideoCodec codec;

    @JsonProperty("CodecOptions")
    private VideoCodecOptions codecOptions;

    @JsonProperty("KeyframesMaxDist")
    @JsonSerialize(using = IntegerToStringSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer keyframesMaxDist;

    @JsonProperty("FixedGOP")
    private Boolean fixedGOP;

    @JsonProperty("BitRate")
    @JsonSerialize(using = IntegerToStringSerializer.class, nullsUsing = NullToAutoSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer bitRate;

    @JsonProperty("FrameRate")
    private FrameRate frameRate;

    @JsonProperty("MaxFrameRate")
    private MaxFrameRate maxFrameRate;

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

    @JsonProperty("DisplayAspectRatio")
    private AspectRatio displayAspectRatio;

    @JsonProperty("Resolution")
    @JsonSerialize(nullsUsing = NullToAutoSerializer.class)
    private String resolution;

    @JsonProperty("AspectRatio")
    private AspectRatio aspectRatio;

    @JsonProperty("Watermarks")
    private List<VideoWatermark> watermarks;

    public Video(VideoCodec codec,
                 FrameRate frameRate,
                 MaxFrameRate maxFrameRate,
                 SizingPolicy sizingPolicy,
                 PaddingPolicy paddingPolicy,
                 AspectRatio displayAspectRatio) {
        checkNotNull(codec, "codec must not be null");
        checkNotNull(frameRate, "frameRate must not be null");
        checkNotNull(maxFrameRate, "maxFrameRate must not be null");
        checkNotNull(sizingPolicy, "sizingPolicy must not be null");
        checkNotNull(paddingPolicy, "paddingPolicy must not be null");
        checkNotNull(displayAspectRatio, "displayAspectRatio must not be null");

        this.codec = codec;
        this.frameRate = frameRate;
        this.maxFrameRate = maxFrameRate;
        this.sizingPolicy = sizingPolicy;
        this.paddingPolicy = paddingPolicy;
        this.displayAspectRatio = displayAspectRatio;
    }

    public Video(VideoCodec codec,
                 FrameRate frameRate,
                 MaxFrameRate maxFrameRate,
                 AspectRatio aspectRatio) {
        checkNotNull(codec, "codec must not be null");
        checkNotNull(frameRate, "frameRate must not be null");
        checkNotNull(maxFrameRate, "maxFrameRate must not be null");
        checkNotNull(aspectRatio, "aspectRatio must not be null");

        this.codec = codec;
        this.frameRate = frameRate;
        this.maxFrameRate = maxFrameRate;
        this.aspectRatio = aspectRatio;
    }

    public void setCodecOptions(VideoCodecOptions codecOptions) {
        this.codecOptions = codecOptions;
    }

    public void setKeyframesMaxDist(Integer keyframesMaxDist) {
        this.keyframesMaxDist = keyframesMaxDist;
    }

    public void setFixedGOP(Boolean fixedGOP) {
        this.fixedGOP = fixedGOP;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
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

    public void setWatermarks(List<VideoWatermark> watermarks) {
        this.watermarks = watermarks;
    }
}
