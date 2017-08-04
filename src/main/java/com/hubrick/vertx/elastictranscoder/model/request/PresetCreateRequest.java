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
package com.hubrick.vertx.elastictranscoder.model.request;

import com.hubrick.vertx.elastictranscoder.model.AbstractPreset;
import com.hubrick.vertx.elastictranscoder.model.Audio;
import com.hubrick.vertx.elastictranscoder.model.PresetContainer;
import com.hubrick.vertx.elastictranscoder.model.Thumbnail;
import com.hubrick.vertx.elastictranscoder.model.Video;
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
public class PresetCreateRequest extends AbstractPreset {

    public PresetCreateRequest(String name, String description, PresetContainer container) {
        checkNotNull(StringUtils.trimToNull(name), "name must not be empty");
        checkNotNull(StringUtils.trim(description), "description must not be empty");
        checkNotNull(container, "container must not be null");

        this.name = name;
        this.description = description;
        this.container = container;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}
