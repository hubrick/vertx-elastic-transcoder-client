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
public class Permission {

    @JsonProperty("GranteeType")
    private GranteeType granteeType;

    @JsonProperty("Grantee")
    private String grantee;

    @JsonProperty("Access")
    private Access access;

    public Permission(GranteeType granteeType, String grantee, Access access) {
        checkNotNull(granteeType, "granteeType must not be null");
        checkNotNull(StringUtils.trimToNull(grantee), "grantee must not be empty");
        checkNotNull(access, "access must not be null");

        this.granteeType = granteeType;
        this.grantee = grantee;
        this.access = access;
    }
}
