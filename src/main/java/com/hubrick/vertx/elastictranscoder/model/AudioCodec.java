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
public enum AudioCodec {
    AC_AAC("AAC"),
    AC_FLAC("flac"),
    AC_MP2("mp2"),
    AC_MP3("mp3"),
    AC_PCM("pcm"),
    AC_VORBIS("vorbis");

    private final String value;

    AudioCodec(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
