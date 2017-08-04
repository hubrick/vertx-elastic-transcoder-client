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
package com.hubrick.vertx.elastictranscoder.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
public class LocalTimeToStringSerializer extends JsonSerializer<LocalTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Override
    public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if(localTime != null) {
            jsonGenerator.writeString(localTime.format(formatter));
        }
    }
}
