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
package com.anrisoftware.globalpom.core.resources

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameter
import org.junit.runners.Parameterized.Parameters

import com.anrisoftware.globalpom.core.resources.StringToURI

import groovy.util.logging.Slf4j

/**
 * @see StringToURI
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
@RunWith(Parameterized.class)
class StringToURITest {

    @Parameters(name = "{index}: {0}={1}")
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

    @Parameter(0)
    public String path

    @Parameter(1)
    public URI expected

    @Test
    void "convert to URI"() {
        assert StringToURI.toURI(path) == expected
    }
}
