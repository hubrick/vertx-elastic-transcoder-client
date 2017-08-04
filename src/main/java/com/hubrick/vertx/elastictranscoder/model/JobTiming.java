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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubrick.vertx.elastictranscoder.deserializer.MillisecondsEpochStringToInstantDeserializer;
import com.hubrick.vertx.elastictranscoder.serializer.InstantToStringMillisecondsEpochSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobTiming {

    @JsonProperty("SubmitTimeMillis")
    @JsonSerialize(using = InstantToStringMillisecondsEpochSerializer.class)
    @JsonDeserialize(using = MillisecondsEpochStringToInstantDeserializer.class)
    private Instant submitTimeMillis;

    @JsonProperty("StartTimeMillis")
    @JsonSerialize(using = InstantToStringMillisecondsEpochSerializer.class)
    @JsonDeserialize(using = MillisecondsEpochStringToInstantDeserializer.class)
    private Instant startTimeMillis;

    @JsonProperty("FinishTimeMillis")
    @JsonSerialize(using = InstantToStringMillisecondsEpochSerializer.class)
    @JsonDeserialize(using = MillisecondsEpochStringToInstantDeserializer.class)
    private Instant finishTimeMillis;
}
