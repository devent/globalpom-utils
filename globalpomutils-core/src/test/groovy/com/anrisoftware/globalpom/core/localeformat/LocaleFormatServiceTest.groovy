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
package com.anrisoftware.globalpom.core.localeformat

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.utils.TestUtils

import groovy.util.logging.Slf4j

/**
 * @see DurationSimpleFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
class LocaleFormatServiceTest {

    final OsgiContext context = new OsgiContext()

    @Test
    void "format duration"() {
        new tests_formats().run().each {
            def str = service.create().format(it.value)
            log.info "Format {} {} as '{}'", it.value.getClass(), it.value, str
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse duration"() {
        new tests_formats().run().each {
            def value = service.create().parse(it.format)
            log.info "Parse '{}' as {} {}", it.format, value.getClass(), value
            assert value == it.value
        }
    }

    LocaleFormatService service

    @BeforeEach
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new LocaleFormatServiceImpl(), null)
    }
}
