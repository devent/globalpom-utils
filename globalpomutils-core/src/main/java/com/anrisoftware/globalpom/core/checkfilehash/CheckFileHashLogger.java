/*
 * Copyright 2013-2023 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.checkfilehash;

import static com.anrisoftware.globalpom.core.checkfilehash.CheckFileHashLogger.m.filemnull;
import static com.anrisoftware.globalpom.core.checkfilehash.CheckFileHashLogger.m.hashmmatching;
import static com.anrisoftware.globalpom.core.checkfilehash.CheckFileHashLogger.m.resourcemnull;
import static org.apache.commons.lang3.Validate.notNull;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

import jakarta.inject.Inject;

import com.anrisoftware.globalpom.core.resources.ToURIFactory;
import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link CheckFileHash}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
class CheckFileHashLogger extends AbstractLogger {

    private static final String CHARSETmARG = "charset";
    private static final String HASH = "hash";
    private static final String FILE = "file";

    enum m {

        filemnull("File cannot be null for %s."),

        resourcemnull("Hash resource cannot be null for %s."),

        hashmmatching("Hash {} compared to hash {} matches {} for {}.");

        private String name;

        private m(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Inject
    private ToURIFactory toURI;

    /**
     * Sets the context of the logger to {@link CheckFileHash}.
     */
    public CheckFileHashLogger() {
        super(CheckFileHash.class);
    }

    URI file(Object script, Map<String, Object> args) {
        Object file = args.get(FILE);
        notNull(file, filemnull.toString(), script);
        return toURI.create(file.toString()).convert();
    }

    URI hash(Object script, Map<String, Object> args) {
        Object resource = args.get(HASH);
        notNull(resource, resourcemnull.toString(), script);
        return toURI.create(resource).convert();
    }

    void hashMatching(CheckFileHash check, String expected, String hashstr, boolean matching) {
        info(hashmmatching, expected, hashstr, matching, check);
    }

    Charset charset(Object script, Map<String, Object> args) {
        Object charset = args.get(CHARSETmARG);
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        return (Charset) charset;
    }
}
