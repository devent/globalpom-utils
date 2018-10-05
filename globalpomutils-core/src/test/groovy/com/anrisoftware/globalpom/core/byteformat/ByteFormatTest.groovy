package com.anrisoftware.globalpom.core.byteformat


import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

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
        new tests_formats().run().each {
            def str = formatFactory.create().format(it.value, it.multiplier)
            log.info "Format {} {} as '{}'", it.value, it.multiplier, str
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse bytes"() {
        new tests_parses().run().each {
            log.info "Parse '{}'", it.input
            def value = formatFactory.create().parse(it.input)
            assert value == it.value
        }
    }

    @Test
    void "parse bytes multiplier"() {
        new tests_parses_multiplier().run().each {
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

    static Injector injector

    static ByteFormatFactory formatFactory

    @BeforeAll
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new ByteFormatModule())
        formatFactory = injector.getInstance ByteFormatFactory
    }
}
