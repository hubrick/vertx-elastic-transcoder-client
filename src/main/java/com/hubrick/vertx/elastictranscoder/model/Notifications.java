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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notifications {

    @JsonProperty("Progressing")
    private String progressing;

    @JsonProperty("Completed")
    private String completed;

    @JsonProperty("Warning")
    private String warning;

    @JsonProperty("Error")
    private String error;

    public Notifications(String progressing, String completed, String warning, String error) {
        checkNotNull(StringUtils.trimToNull(progressing), "progressing must not be empty");
        checkNotNull(StringUtils.trimToNull(completed), "completed must not be empty");
        checkNotNull(StringUtils.trimToNull(warning), "warning must not be empty");
        checkNotNull(StringUtils.trimToNull(error), "error must not be empty");

        this.progressing = progressing;
        this.completed = completed;
        this.warning = warning;
        this.error = error;
    }
}
