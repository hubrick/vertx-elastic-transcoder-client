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
public class Audio {

    @JsonProperty("Codec")
    private AudioCodec codec;

    @JsonProperty("CodecOptions")
    private AudioCodecOptions codecOptions;

    @JsonProperty("SampleRate")
    private SampleRate sampleRate;

    @JsonProperty("BitRate")
    @JsonSerialize(using = IntegerToStringSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer bitRate;

    @JsonProperty("Channels")
    private Channels channels;

    @JsonProperty("AudioPackingMode")
    private AudioPackingMode audioPackingMode;

    public Audio(AudioCodec codec, SampleRate sampleRate, Integer bitRate, Channels channels) {
        checkNotNull(codec, "codes must not be empty");
        checkNotNull(sampleRate, "sampleRate must not be empty");
        checkNotNull(bitRate, "bitRate must not be empty");
        checkNotNull(channels, "channels must not be empty");

        this.codec = codec;
        this.sampleRate = sampleRate;
        this.bitRate = bitRate;
        this.channels = channels;
    }

    public void setCodecOptions(AudioCodecOptions codecOptions) {
        this.codecOptions = codecOptions;
    }

    public void setAudioPackingMode(AudioPackingMode audioPackingMode) {
        this.audioPackingMode = audioPackingMode;
    }
}
