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
public enum ColorSpaceConversion {
    AUTO("Auto"),
    CSC_BT709_TO_BT601("Bt709ToBt601"),
    CSC_BT601_TO_BT709("Bt601ToBt709"),
    CSC_NONE("None");

    private final String value;

    ColorSpaceConversion(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
