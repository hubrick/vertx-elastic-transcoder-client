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

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Emir Dizdarevic
 * @since 1.0.0
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PutObjectRequest {

    private Buffer data;

    private String cacheControl;
    private String contentDisposition;
    private String contentEncoding;
    private String contentMD5;
    private String contentType;
    private String expires;

    private MultiMap amzMeta = MultiMap.caseInsensitiveMultiMap();
    private String amzStorageClass;
    private String amzTagging;
    private String amzWebsiteRedirectLocation;

    private String amzAcl;
    private String amzGrantRead;
    private String amzGrantWrite;
    private String amzGrantReadAcp;
    private String amzGrantWriteAcp;
    private String amzGrantFullControl;

    public PutObjectRequest(Buffer data) {
        checkNotNull(data, "data must not be null");

        this.data = data;
    }

    public PutObjectRequest withCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
        return this;
    }

    public PutObjectRequest withContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
        return this;
    }

    public PutObjectRequest withContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
        return this;
    }

    public PutObjectRequest withContentMD5(String contentMD5) {
        this.contentMD5 = contentMD5;
        return this;
    }

    public PutObjectRequest withContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public PutObjectRequest withExpires(String expires) {
        this.expires = expires;
        return this;
    }

    public PutObjectRequest withAmzMeta(String key, String value) {
        this.amzMeta.add(key, value);
        return this;
    }

    public PutObjectRequest withAmzMeta(MultiMap amzMeta) {
        this.amzMeta.addAll(amzMeta);
        return this;
    }

    public PutObjectRequest withAmzStorageClass(String amzStorageClass) {
        this.amzStorageClass = amzStorageClass;
        return this;
    }

    public PutObjectRequest withAmzTagging(String amzTagging) {
        this.amzTagging = amzTagging;
        return this;
    }

    public PutObjectRequest withAmzWebsiteRedirectLocation(String amzWebsiteRedirectLocation) {
        this.amzWebsiteRedirectLocation = amzWebsiteRedirectLocation;
        return this;
    }

    public PutObjectRequest withAmzAcl(String amzAcl) {
        this.amzAcl = amzAcl;
        return this;
    }

    public PutObjectRequest withAmzGrantRead(String amzGrantRead) {
        this.amzGrantRead = amzGrantRead;
        return this;
    }

    public PutObjectRequest withAmzGrantWrite(String amzGrantWrite) {
        this.amzGrantWrite = amzGrantWrite;
        return this;
    }

    public PutObjectRequest withAmzGrantReadAcp(String amzGrantReadAcp) {
        this.amzGrantReadAcp = amzGrantReadAcp;
        return this;
    }

    public PutObjectRequest withAmzGrantWriteAcp(String amzGrantWriteAcp) {
        this.amzGrantWriteAcp = amzGrantWriteAcp;
        return this;
    }

    public PutObjectRequest withAmzGrantFullControl(String amzGrantFullControl) {
        this.amzGrantFullControl = amzGrantFullControl;
        return this;
    }
}
