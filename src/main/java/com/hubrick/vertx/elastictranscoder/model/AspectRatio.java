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
public enum AspectRatio {
    AUTO("auto"),
    A_1_1("1:1"),
    A_4_3("4:3"),
    A_3_2("3:2"),
    A_16_9("16:9");

    private final String value;

    AspectRatio(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
