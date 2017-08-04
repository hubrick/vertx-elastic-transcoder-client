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
import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumArtArtwork {

    @JsonProperty("AlbumArtInputKey")
    private String albumArtInputKey;

    @JsonProperty("Encryption")
    private Encryption encryption;

    @JsonProperty("AlbumArtMaxWidth")
    @JsonSerialize(using = IntegerToStringSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer albumArtMaxWidth;

    @JsonProperty("AlbumArtMaxHeight")
    @JsonSerialize(using = IntegerToStringSerializer.class)
    @JsonDeserialize(using = StringToIntegerDeserializer.class)
    private Integer albumArtMaxHeight;

    @JsonProperty("AlbumArtSizingPolicy")
    private SizingPolicy albumArtSizingPolicy;

    @JsonProperty("AlbumArtPaddingPolicy")
    private PaddingPolicy albumArtPaddingPolicy;

    @JsonProperty("AlbumArtFormat")
    private ImageFormat albumArtFormat;

    public AlbumArtArtwork(String albumArtInputKey,
                           Integer albumArtMaxWidth,
                           Integer albumArtMaxHeight,
                           SizingPolicy albumArtSizingPolicy,
                           PaddingPolicy albumArtPaddingPolicy,
                           ImageFormat albumArtFormat) {
        checkNotNull(StringUtils.trimToNull(albumArtInputKey), "albumArtInputKey must not be empty");
        checkNotNull(albumArtMaxWidth, "albumArtMaxWidth must not be null");
        checkNotNull(albumArtMaxHeight, "albumArtMaxHeight must not be null");
        checkNotNull(albumArtSizingPolicy, "albumArtSizingPolicy must not be null");
        checkNotNull(albumArtPaddingPolicy, "albumArtPaddingPolicy must not be null");
        checkNotNull(albumArtFormat, "albumArtFormat must not be null");

        this.albumArtInputKey = albumArtInputKey;
        this.albumArtMaxWidth = albumArtMaxWidth;
        this.albumArtMaxHeight = albumArtMaxHeight;
        this.albumArtSizingPolicy = albumArtSizingPolicy;
        this.albumArtPaddingPolicy = albumArtPaddingPolicy;
        this.albumArtFormat = albumArtFormat;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }
}
