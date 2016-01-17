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

import org.apache.commons.io.Charsets
import org.junit.BeforeClass
import org.junit.Test

import com.google.inject.Injector

/**
 * @see PosixLocale
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.6
 */
@Slf4j
class PosixLocaleTest {

    @Test
    void "locale, default charset"() {
        def locale = factory.create Locale.US
        assert locale.locale == Locale.US
        assert locale.charset == Charset.defaultCharset()
    }

    @Test
    void "locale, charset"() {
        def locale = factory.create Locale.US, Charsets.UTF_8
        assert locale.locale == Locale.US
        assert locale.charset == Charsets.UTF_8
    }

    @Test
    void "serialize"() {
        def localea = factory.create Locale.US, Charsets.UTF_8
        def localeb = reserialize localea
        assert localea == localeb
    }

    @Test
    void "equal"() {
        def localea = factory.create Locale.US, Charsets.UTF_8
        def localeb = factory.create Locale.US, Charsets.UTF_8
        assert localea == localeb
    }

    @Test
    void "not equal on locale"() {
        def localea = factory.create Locale.US, Charsets.UTF_8
        def localeb = factory.create Locale.GERMAN, Charsets.UTF_8
        assert localea != localeb
    }

    @Test
    void "not equal on charset"() {
        def localea = factory.create Locale.US, Charsets.UTF_8
        def localeb = factory.create Locale.US, Charsets.ISO_8859_1
        assert localea != localeb
    }

    static Injector injector

    static PosixLocaleFactory factory

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.injector = createInjector new PosixLocaleModule()
        this.factory = injector.getInstance PosixLocaleFactory
    }
}
