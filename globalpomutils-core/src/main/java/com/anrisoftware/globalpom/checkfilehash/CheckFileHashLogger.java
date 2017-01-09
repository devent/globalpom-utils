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

import static com.anrisoftware.globalpom.checkfilehash.CheckFileHashLogger._.file_null;
import static com.anrisoftware.globalpom.checkfilehash.CheckFileHashLogger._.hash_matching;
import static com.anrisoftware.globalpom.checkfilehash.CheckFileHashLogger._.resource_null;
import static org.apache.commons.lang3.Validate.notNull;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

import javax.inject.Inject;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.resources.ToURIFactory;

/**
 * Logging for {@link CheckFileHash}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
class CheckFileHashLogger extends AbstractLogger {

    private static final String CHARSET_ARG = "charset";
    private static final String HASH = "hash";
    private static final String FILE = "file";

    enum _ {

        file_null("File cannot be null for %s."),

        resource_null("Hash resource cannot be null for %s."),

        hash_matching("Hash {} compared to hash {} matches {} for {}.");

        private String name;

        private _(String name) {
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
        notNull(file, file_null.toString(), script);
        return toURI.create().convert(file.toString());
    }

    URI hash(Object script, Map<String, Object> args) {
        Object resource = args.get(HASH);
        notNull(resource, resource_null.toString(), script);
        return toURI.create().convert(resource);
    }

    void hashMatching(CheckFileHash check, String expected, String hashstr,
            boolean matching) {
        info(hash_matching, expected, hashstr, matching, check);
    }

    Charset charset(Object script, Map<String, Object> args) {
        Object charset = args.get(CHARSET_ARG);
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        return (Charset) charset;
    }
}
