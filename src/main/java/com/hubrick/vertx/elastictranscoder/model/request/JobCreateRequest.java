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
package com.hubrick.vertx.elastictranscoder.model.request;

import com.hubrick.vertx.elastictranscoder.model.AbstractJob;
import com.hubrick.vertx.elastictranscoder.model.JobInput;
import com.hubrick.vertx.elastictranscoder.model.JobOutput;
import com.hubrick.vertx.elastictranscoder.model.Playlist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobCreateRequest extends AbstractJob {

    public JobCreateRequest(List<JobInput> inputs, String outputKeyPrefix, List<JobOutput> outputs, String pipelineId) {
        checkNotNull(inputs, "inputs must not be null");
        checkNotNull(!inputs.isEmpty(), "inputs must not be empty");
        checkArgument(outputKeyPrefix == null || StringUtils.trimToNull(outputKeyPrefix) != null, "outputKeyPrefix must be either 'null' or have some content");
        checkNotNull(outputs, "outputs must not be null");
        checkNotNull(!outputs.isEmpty(), "outputs must not be empty");
        checkNotNull(StringUtils.trimToNull(pipelineId), "pipelineId must not be empty");

        this.inputs = inputs;
        this.outputKeyPrefix = outputKeyPrefix;
        this.outputs = outputs;
        this.pipelineId = pipelineId;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setUserMetadata(Map<String, String> userMetadata) {
        this.userMetadata = userMetadata;
    }
}
