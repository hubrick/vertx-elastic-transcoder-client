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
public enum FrameRate {
    AUTO("auto"),
    FR_10("10"),
    FR_15("15"),
    FR_23_97("23.97"),
    FR_24("24"),
    FR_25("25"),
    FR_29_97("29.97"),
    FR_30("30"),
    FR_50("50"),
    FR_60("60");

    private final String value;

    FrameRate(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
