/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.format.locale.LocaleFormatFactory
import com.anrisoftware.globalpom.format.locale.LocaleFormatModule
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
        formats.each {
            def str = formatFactory.create().format(it.value)
            log.info "Format {} {} as '{}'", it.value.getClass(), it.value, str
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse duration"() {
        formats.each {
            def value = formatFactory.create().parse(it.format)
            log.info "Parse '{}' as {} {}", it.format, value.getClass(), value
            assert value == it.value
        }
    }

    static formats

    static Injector injector

    static LocaleFormatFactory formatFactory

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new LocaleFormatModule())
        formatFactory = injector.getInstance LocaleFormatFactory
        formats  = [
            [format: "en", value: Locale.ENGLISH],
            [format: "de", value: Locale.GERMAN],
            [format: "de_DE", value: Locale.GERMANY],
        ]
    }
}
