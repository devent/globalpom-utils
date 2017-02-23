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
package com.anrisoftware.globalpom.core.posixlocale

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import com.anrisoftware.globalpom.core.posixlocale.PosixLocaleFormatService
import com.anrisoftware.globalpom.core.posixlocale.PosixLocaleFormatServiceImpl
import com.anrisoftware.globalpom.core.posixlocale.PosixLocaleService
import com.anrisoftware.globalpom.core.posixlocale.PosixLocaleServiceImpl
import com.anrisoftware.globalpom.utils.TestUtils

/**
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
class PosixLocaleFormatServiceTest {

    @Rule
    public final OsgiContext context = new OsgiContext()

    @Test
    void "formats locale"() {
        def formats = new tests_formats()
        formats.localeFactory = service
        List<Map> cases = formats.run()
        cases.eachWithIndex { it, k ->
            log.info "{}. test case: format {} to expected '{}'", k, it.input, it.expected
            def str = formatService.create().format(it.input)
            assert str == it.expected
        }
    }

    @Test
    void "parses locale"() {
        def formats = new tests_parses()
        formats.localeFactory = service
        List<Map> cases = formats.run()
        cases.eachWithIndex { it, k ->
            log.info "{}. test case: parse '{}' to expected {}", k, it.input, it.expected
            def locale = formatService.create().parse(it.input)
            assert locale == it.expected
        }
    }

    PosixLocaleService service

    PosixLocaleFormatService formatService

    @Before
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new PosixLocaleServiceImpl(), null)
        this.formatService = context.registerInjectActivateService(new PosixLocaleFormatServiceImpl(), null)
    }
}
