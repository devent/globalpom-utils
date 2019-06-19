/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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
package com.anrisoftware.globalpom.core.resources

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import groovy.util.logging.Slf4j

/**
 * @see StringToURI
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class StringToURITest {

    static Collection<Object[]> data() {
        [
            [
                "file.txt",
                new URI("file:file.txt")
            ],
            [
                "file://file.txt",
                new URI("file://file.txt")
            ],
            [
                "file:/file.txt",
                new URI("file:///file.txt")
            ],
        ] as Object[][]
    }

    @ParameterizedTest(name = "{index} => path={0}, expected={1}")
    @MethodSource("data")
    void "convert to URI"(String path, URI expected) {
        assert StringToURI.toURI(path) == expected
    }
}
