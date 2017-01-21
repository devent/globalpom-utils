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
package com.anrisoftware.globalpom.resources

import org.junit.Test

import groovy.util.logging.Slf4j

/**
 * @see ToURI
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class ToURITest {

    @Test
    void "convert to URI"() {
        inputs.each {
            def uri = ToURI.toURI(it.path)
            log.info "Converted path '{}' to '{}'", it.path, uri
            assert uri == it.uri
        }
    }

    static inputs = [
        [path: "file.txt", uri: new URI("file:file.txt")],
        [path: "file://file.txt", uri: new URI("file://file.txt")],
        [path: new File("file.txt"), uri: new File("file.txt").toURI()],
        [path: new URL("file://file.txt"), uri: new URI("file://file.txt")],
        [path: new URI("file://file.txt"), uri: new URI("file://file.txt")],
    ]
}
