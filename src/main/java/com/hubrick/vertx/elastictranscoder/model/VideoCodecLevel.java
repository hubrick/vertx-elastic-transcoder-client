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
public enum VideoCodecLevel {
    VCL_1("1"),
    VCL_1B("1b"),
    VCL_1_1("1.1"),
    VCL_1_2("1.2"),
    VCL_1_3("1.3"),
    VCL_2("2"),
    VCL_2_1("2.1"),
    VCL_2_2("2.2"),
    VCL_3("3"),
    VCL_3_1("3.1"),
    VCL_3_2("3.2"),
    VCL_4("4"),
    VCL_4_1("4.1");

    private final String value;

    VideoCodecLevel(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
