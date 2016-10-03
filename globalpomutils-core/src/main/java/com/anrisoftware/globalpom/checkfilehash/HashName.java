/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.checkfilehash;

/**
 * File extensions and hash algorithm names.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
enum HashName {

    /**
     * MD-5 algorithm.
     */
    md5("md5", "MD5"),

    /**
     * SHA-1 algorithm.
     */
    sha1("sha1", "SHA-1");

    private String fileExtention;

    private String hashName;

    private HashName(String fileExtention, String hashName) {
        this.fileExtention = fileExtention;
        this.hashName = hashName;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public String getHashName() {
        return hashName;
    }

    /**
     * Returns the hash algorithm name for the specified file extension.
     *
     * @param ex
     *            the {@link String} file extension.
     *
     * @return the {@link HashName} or {@code null}.
     */
    public static HashName forExtension(String ex) {
        for (HashName hash : values()) {
            if (ex.equalsIgnoreCase(hash.getFileExtention())) {
                return hash;
            }
        }
        return null;
    }
}
