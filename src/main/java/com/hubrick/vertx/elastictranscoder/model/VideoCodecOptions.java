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
import com.hubrick.vertx.elastictranscoder.deserializer.StringToInfiniteIntegerDeserializer;
import com.hubrick.vertx.elastictranscoder.deserializer.StringToIntegerDeserializer;
import com.hubrick.vertx.elastictranscoder.serializer.InfiniteIntegerToStringSerializer;
import com.hubrick.vertx.elastictranscoder.serializer.IntegerToStringSerializer;
import lombok.Data;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Data
public class VideoCodecOptions {

    @JsonProperty("Profile")
    private VideoCodecProfile profile;

    @JsonProperty("Level")
    private VideoCodecLevel level;

    @JsonProperty("MaxReferenceFrames")
    @JsonSerialize(using = IntegerToStringSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer maxReferenceFrames;

    @JsonProperty("MaxBitRate")
    @JsonSerialize(using = IntegerToStringSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer maxBitRate;

    @JsonProperty("BufferSize")
    @JsonSerialize(using = IntegerToStringSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer bufferSize;

    @JsonProperty("InterlacedMode")
    private InterlacedMode interlacedMode;

    @JsonProperty("ColorSpaceConversion")
    private ColorSpaceConversion colorSpaceConversion;

    @JsonProperty("ChromaSubsampling")
    private ChromaSubsampling chromaSubsampling;

    @JsonProperty("LoopCount")
    @JsonSerialize(using = InfiniteIntegerToStringSerializer.class)
    @JsonDeserialize(using = StringToInfiniteIntegerDeserializer.class)
    private Integer loopCount;
}
