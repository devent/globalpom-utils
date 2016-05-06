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
package com.anrisoftware.globalpom.durationsimpleformat

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see DurationSimpleFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.5
 */
@Slf4j
class DurationSimpleFormatTest {

    @Test
    void "format simple duration"() {
        new tests_formats().run().each {
            def str = formatFactory.create().format(it.value, it.multiplier)
            log.info "Format {}{} as '{}'", it.value, it.multiplier, str
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse simple duration"() {
        new tests_parses().run().each {
            log.info "Parse '{}'", it.input
            def value = formatFactory.create().parse(it.input)
            assert value == it.value
        }
    }

    @Test
    void "parse simple duration multiplier"() {
        new tests_parses_multiplier().run().each {
            log.info "Parse '{}' with multiplier {}", it.input, it.multiplier
            def value = formatFactory.create().parse(it.input, it.multiplier)
            assert value == it.value
        }
    }

    @Test
    void "roundSize"() {
        def cases = [
            [size: 1, expected: "1ms"],
            [size: 1005, expected: "1s"],
            [size: 1005 * 60, expected: "1m"],
            [size: 1005 * 60 * 60, expected: "1h"],
            [size: 1005 * 60 * 60 * 24, expected: "1d"],
            [size: 1005 * 60 * 60 * 24 * 7, expected: "1w"],
            [size: 1005.0 * 60 * 60 * 24 * 30, expected: "1M"],
            [size: 1005.0 * 60 * 60 * 24 * 365, expected: "1y"],
        ]
        cases.each {
            log.info "Round '{}', expected '{}'", it.size, it.expected
            def res = DurationSimpleFormat.roundSizeUnit((long)it.size)
            assert res == it.expected
        }
    }

    static Injector injector

    static DurationSimpleFormatFactory formatFactory

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector new DurationSimpleFormatModule()
        formatFactory = injector.getInstance DurationSimpleFormatFactory
    }
}
