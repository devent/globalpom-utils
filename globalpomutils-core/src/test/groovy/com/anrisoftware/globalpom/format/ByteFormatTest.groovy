/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.format

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.format.byteformat.ByteFormat
import com.anrisoftware.globalpom.format.byteformat.ByteFormatFactory
import com.anrisoftware.globalpom.format.byteformat.ByteFormatModule
import com.anrisoftware.globalpom.format.byteformat.UnitMultiplier
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see ByteFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class ByteFormatTest {

    @Test
    void "format bytes"() {
        formats.each {
            def str = formatFactory.create().format(it.value, it.multiplier)
            log.info "Format {} {} as '{}'", it.value, it.multiplier, str
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse bytes"() {
        parses.each {
            log.info "Parse '{}'", it.input
            def value = formatFactory.create().parse(it.input)
            assert value == it.value
        }
    }

    @Test
    void "parse bytes multiplier"() {
        parsesMultiplier.each {
            log.info "Parse '{}' with multiplier {}", it.input, it.multiplier
            def value = formatFactory.create().parse(it.input, it.multiplier)
            assert value == it.value
        }
    }

    @Test
    void "roundSizeSI"() {
        def cases = [
            [size: 1, expected: "1"],
            [size: 1005, expected: "1k"],
            [size: 12005, expected: "12k"],
            [size: 122005, expected: "122k"],
            [size: 1220005, expected: "1M"],
            [size: 12200005, expected: "12M"],
        ]
        cases.each {
            log.info "Round '{}', expected '{}'", it.size, it.expected
            def res = ByteFormat.roundSizeSI(it.size)
            assert res == it.expected
        }
    }

    static formats

    static parses

    static parsesMultiplier

    static Injector injector

    static ByteFormatFactory formatFactory

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new ByteFormatModule())
        formatFactory = injector.getInstance ByteFormatFactory
        formats  = [
            [format: "64 byte", value: 64, multiplier: UnitMultiplier.ONE],
            [format: "64 kB", value: 64, multiplier: UnitMultiplier.KILO],
            [format: "64 MB", value: 64, multiplier: UnitMultiplier.MEGA],
            [format: "64 KiB", value: 64, multiplier: UnitMultiplier.KIBI],
            [format: "64 MiB", value: 64, multiplier: UnitMultiplier.MEBI],
        ]
        parses = [
            [input: "64", value: 64],
            [input: "64 byte", value: 64],
            [input: "64 kB", value: 64 * 1000**1],
            [input: "64 MB", value: 64 * 1000**2],
            [input: "64 KiB", value: 64 * 1024**1],
            [input: "64 MiB", value: 64 * 1024**2],
        ]
        parsesMultiplier = [
            [input: "64 byte", value: 64, multiplier: UnitMultiplier.ONE],
            [input: "64 kB", value: 64, multiplier: UnitMultiplier.KILO],
            [input: "64 MB", value: 64, multiplier: UnitMultiplier.MEGA],
            [input: "64 KiB", value: 64, multiplier: UnitMultiplier.KIBI],
            [input: "64 MiB", value: 64, multiplier: UnitMultiplier.MEBI],
        ]
    }
}
