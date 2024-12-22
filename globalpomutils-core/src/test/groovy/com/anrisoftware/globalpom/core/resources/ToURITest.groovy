/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.Test

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
        [
            [path: "file.txt", uri: new URI("file:file.txt")],
            [path: "id_rsa", uri: new URI("file:id_rsa")],
            [path: "file://file.txt", uri: new URI("file://file.txt")],
            [path: new File("file.txt"), uri: new File("file.txt").toURI()],
            [path: new URL("file://file.txt"), uri: new URI("file://file.txt")],
            [path: new URI("file://file.txt"), uri: new URI("file://file.txt")],
            [path: "github.com:devent/wordpress-app-test.git", uri: new URI("github.com:devent/wordpress-app-test.git")],
            [path: "github.com:/devent/wordpress-app-test.git", uri: new URI("github.com:/devent/wordpress-app-test.git")],
        ].eachWithIndex { it, int k ->
            log.info '\n######### {}. case: {}', k, it
            def uri = ToURI.toURI(it.path).convert()
            assert uri == it.uri
        }
    }

    @Test
    void "with scp"() {
        [
            [path: "file.txt", uri: new URI("file:file.txt")],
            [path: "file://file.txt", uri: new URI("file://file.txt")],
            [path: new File("file.txt"), uri: new File("file.txt").toURI()],
            [path: new URL("file://file.txt"), uri: new URI("file://file.txt")],
            [path: new URI("file://file.txt"), uri: new URI("file://file.txt")],
            [path: "git@github.com:devent/wordpress-app-test.git", uri: new URI("ssh://git@github.com/devent/wordpress-app-test.git")],
            [path: "github.com:devent/wordpress-app-test.git", uri: new URI("ssh://github.com/devent/wordpress-app-test.git")],
            [path: "github.com:/devent/wordpress-app-test.git", uri: new URI("github.com:/devent/wordpress-app-test.git")],
        ].eachWithIndex { it, int k ->
            log.info '\n######### {}. case: {}', k, it
            def uri = ToURI.toURI(it.path).withScp().convert()
            assert uri == it.uri
        }
    }

    @Test
    void "expected ConvertException"() {
        [
            [path: "git@github.com:/devent/wordpress-app-test.git"],
        ].eachWithIndex { test, int k ->
            log.info '\n######### {}. case: {}', k, test
            shouldFailWith ConvertException, {
                def uri = ToURI.toURI(test.path).convert()
            }
        }
    }
}
