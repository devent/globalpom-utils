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

package com.anrisoftware.globalpom.core.durationformat

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.utils.TestUtils

import groovy.util.logging.Slf4j

/**
 * @see DurationFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
class DurationFormatServiceTest {

    final OsgiContext context = new OsgiContext()

    @Test
    void "format duration"() {
        def format = service.create()
        new tests_formats().run().each {
            log.info "Format '{}'", it.duration
            def str = format.format it.duration
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse duration"() {
        def format = service.create()
        new tests_parses().run().each {
            log.info "Parse '{}'", it.input
            def duration = format.parse it.input
            assert duration.millis == it.duration
        }
    }

    DurationFormatService service

    @BeforeEach
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new DurationFormatServiceImpl(), null)
    }
}
