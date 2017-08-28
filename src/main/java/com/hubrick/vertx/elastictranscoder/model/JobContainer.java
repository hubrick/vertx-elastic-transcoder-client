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
public enum JobContainer {
    AUTO("auto"),
    JC_3GP("3gp"),
    JC_AAC("aac"),
    JC_ASF("asf"),
    JC_AVI("avi"),
    JC_DIVX("divx"),
    JC_FLV("flv"),
    JC_M4A("m4a"),
    JC_MKV("mkv"),
    JC_MOV("mov"),
    JC_MP3("mp3"),
    JC_MP4("mp4"),
    JC_MPEG("mpeg"),
    JC_MPEG_PS("mpeg-ps"),
    JC_MPEG_TS("mpeg-ts"),
    JC_MXF("mxf"),
    JC_OGG("ogg"),
    JC_VOB("vob"),
    JC_WAV("wav"),
    JC_WEBM("webm");

    private final String value;

    JobContainer(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
