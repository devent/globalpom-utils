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
package com.anrisoftware.globalpom.core.localeformat

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.core.localeformat.LocaleFormatFactory
import com.anrisoftware.globalpom.core.localeformat.LocaleFormatModule
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see LocaleFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class LocaleFormatTest {

    @Test
    void "format duration"() {
        new tests_formats().run().each {
            def str = formatFactory.create().format(it.value)
            log.info "Format {} {} as '{}'", it.value.getClass(), it.value, str
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse duration"() {
        new tests_formats().run().each {
            def value = formatFactory.create().parse(it.format)
            log.info "Parse '{}' as {} {}", it.format, value.getClass(), value
            assert value == it.value
        }
    }

    static Injector injector

    static LocaleFormatFactory formatFactory

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new LocaleFormatModule())
        formatFactory = injector.getInstance LocaleFormatFactory
    }
}
