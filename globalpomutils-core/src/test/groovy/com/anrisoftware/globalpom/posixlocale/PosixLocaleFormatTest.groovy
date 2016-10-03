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
package com.anrisoftware.globalpom.posixlocale

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static com.google.inject.Guice.createInjector
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.localeformat.LocaleFormatModule
import com.google.inject.Injector

/**
 * @see PosixLocaleFormat
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.6
 */
@Slf4j
class PosixLocaleFormatTest {

    @Test
    void "formats locale"() {
        def formats = new tests_formats()
        formats.localeFactory = localeFactory
        List<Map> cases = formats.run()
        cases.eachWithIndex { it, k ->
            log.info "{}. test case: format {} to expected '{}'", k, it.input, it.expected
            def str = localeFormatFactory.create().format(it.input)
            assert str == it.expected
        }
    }

    @Test
    void "parses locale"() {
        def formats = new tests_parses()
        formats.localeFactory = localeFactory
        List<Map> cases = formats.run()
        cases.eachWithIndex { it, k ->
            log.info "{}. test case: parse '{}' to expected {}", k, it.input, it.expected
            def locale = localeFormatFactory.create().parse(it.input)
            assert locale == it.expected
        }
    }

    @Test
    void "factory methods"() {
        def format = PosixLocaleFormat.createDurationFormat()
        assert format != null
    }

    static Injector injector

    static PosixLocaleFactory localeFactory

    static PosixLocaleFormatFactory localeFormatFactory

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.injector = createInjector new PosixLocaleModule(),
                new PosixLocaleFormatModule(), new LocaleFormatModule()
        this.localeFactory = injector.getInstance PosixLocaleFactory
        this.localeFormatFactory = injector.getInstance PosixLocaleFormatFactory
    }
}
