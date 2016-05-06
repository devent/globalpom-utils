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
package com.anrisoftware.globalpom.localeformat

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import com.anrisoftware.globalpom.utils.TestUtils

/**
 * @see DurationSimpleFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
class LocaleFormatServiceTest {

    @Rule
    public final OsgiContext context = new OsgiContext()

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

    @Before
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new LocaleFormatServiceImpl(), null)
    }
}
