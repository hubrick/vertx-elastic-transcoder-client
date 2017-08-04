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
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
public abstract class AbstractJob {

    @JsonProperty("Inputs")
    protected List<JobInput> inputs;

    @JsonProperty("OutputKeyPrefix")
    protected String outputKeyPrefix;

    @JsonProperty("Outputs")
    protected List<JobOutput> outputs;

    @JsonProperty("Playlists")
    protected List<Playlist> playlists;

    @JsonProperty("UserMetadata")
    protected Map<String, String> userMetadata;

    @JsonProperty("PipelineId")
    protected String pipelineId;
}
