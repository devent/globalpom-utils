package com.anrisoftware.globalpom.core.localeformat

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

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

    @BeforeAll
    static void createFactories() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new LocaleFormatModule())
        formatFactory = injector.getInstance LocaleFormatFactory
    }
}
