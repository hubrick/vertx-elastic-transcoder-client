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
import com.hubrick.vertx.elastictranscoder.deserializer.StringToDoubleDeserializer;
import com.hubrick.vertx.elastictranscoder.serializer.DoubleToStringSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobOutput {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Encryption")
    private Encryption encryption;

    @JsonProperty("ThumbnailPattern")
    private String thumbnailPattern;

    @JsonProperty("Rotate")
    private Rotate rotate;

    @JsonProperty("PresetId")
    private String presetId;

    @JsonProperty("SegmentDuration")
    @JsonSerialize(using = DoubleToStringSerializer.class)
    @JsonDeserialize(using = StringToDoubleDeserializer.class)
    private Double segmentDuration;

    @JsonProperty("Watermarks")
    private List<JobWatermark> watermarks;

    @JsonProperty("AlbumArt")
    private AlbumArt albumArt;

    @JsonProperty("Duration")
    private Integer duration;

    @JsonProperty("Width")
    private Integer width;

    @JsonProperty("Height")
    private Integer height;

    @JsonProperty("Status")
    private JobOutputStatus status;

    @JsonProperty("StatusDetail")
    private String statusDetail;

    @JsonProperty("Captions")
    private Captions captions;

    public JobOutput(String key, String presetId) {
        checkNotNull(StringUtils.trimToNull(key), "key must not be empty");
        checkNotNull(StringUtils.trimToNull(presetId), "presetId must not be empty");

        this.key = key;
        this.presetId = presetId;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }

    public void setThumbnailPattern(String thumbnailPattern) {
        this.thumbnailPattern = thumbnailPattern;
    }

    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
    }

    public void setSegmentDuration(Double segmentDuration) {
        this.segmentDuration = segmentDuration;
    }

    public void setWatermarks(List<JobWatermark> watermarks) {
        this.watermarks = watermarks;
    }

    public void setAlbumArt(AlbumArt albumArt) {
        this.albumArt = albumArt;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setStatus(JobOutputStatus status) {
        this.status = status;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public void setCaptions(Captions captions) {
        this.captions = captions;
    }
}
