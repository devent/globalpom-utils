/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
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
