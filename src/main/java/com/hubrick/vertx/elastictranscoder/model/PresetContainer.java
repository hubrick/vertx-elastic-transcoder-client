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

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
public enum PresetContainer {
    PC_FLAC("flac"),
    PC_FLV("flv"),
    PC_FMP4("fmp4"),
    PC_GIF("gif"),
    PC_MP3("mp3"),
    PC_MP4("mp4"),
    PC_MPG("mpg"),
    PC_MXF("mxf"),
    PC_OGA("oga"),
    PC_OGG("ogg"),
    PC_TS("ts"),
    PC_WAV("wav"),
    PC_WEBM("webm");

    private final String value;

    PresetContainer(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
