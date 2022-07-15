/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.durationsimpleformat

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

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
        new tests_formats_numbers().run().each {
            def str = formatFactory.create().format(it.value, it.multiplier)
            log.info "Format {}{} as '{}'", it.value, it.multiplier, str
            assertStringContent str, it.format
        }
        new tests_formats_durations().run().each {
            def str = formatFactory.create().format(it.value, it.multiplier)
            log.info "Format '{}' {} as '{}'", it.value, it.multiplier, str
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

    @BeforeAll
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector new DurationSimpleFormatModule()
        formatFactory = injector.getInstance DurationSimpleFormatFactory
    }
}
