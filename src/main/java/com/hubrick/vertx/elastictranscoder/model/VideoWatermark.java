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
public class VideoWatermark {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("MaxWidth")
    private String maxWidth;

    @JsonProperty("MaxHeight")
    private String maxHeight;

    @JsonProperty("SizingPolicy")
    private SizingPolicy sizingPolicy;

    @JsonProperty("HorizontalAlign")
    private HorizontalAlign horizontalAlign;

    @JsonProperty("HorizontalOffset")
    private String horizontalOffset;

    @JsonProperty("VerticalAlign")
    private VerticalAlign verticalAlign;

    @JsonProperty("VerticalOffset")
    private String verticalOffset;

    @JsonProperty("Opacity")
    private Integer opacity;

    @JsonProperty("Target")
    private VideoWatermarkTarget target;

    public VideoWatermark(String id,
                          String maxWidth,
                          String maxHeight,
                          SizingPolicy sizingPolicy,
                          HorizontalAlign horizontalAlign,
                          String horizontalOffset,
                          VerticalAlign verticalAlign,
                          String verticalOffset,
                          Integer opacity,
                          VideoWatermarkTarget target) {
        checkNotNull(StringUtils.trimToNull(id), "id must not be empty");
        checkNotNull(StringUtils.trimToNull(maxWidth), "maxWidth must not be empty");
        checkNotNull(StringUtils.trimToNull(maxHeight), "id must not be empty");
        checkNotNull(sizingPolicy, "sizingPolicy must not be null");
        checkNotNull(horizontalAlign, "horizontalAlign must not be null");
        checkNotNull(StringUtils.trimToNull(horizontalOffset), "horizontalOffset must not be empty");
        checkNotNull(verticalAlign, "verticalAlign must not be null");
        checkNotNull(StringUtils.trimToNull(verticalOffset), "verticalOffset must not be empty");
        checkNotNull(opacity, "opacity must not be null");
        checkNotNull(target, "target must not be null");


        this.id = id;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.sizingPolicy = sizingPolicy;
        this.horizontalAlign = horizontalAlign;
        this.horizontalOffset = horizontalOffset;
        this.verticalAlign = verticalAlign;
        this.verticalOffset = verticalOffset;
        this.opacity = opacity;
        this.target = target;
    }
}
