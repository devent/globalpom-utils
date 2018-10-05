package com.anrisoftware.globalpom.core.posixlocale

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static com.google.inject.Guice.createInjector

import java.nio.charset.StandardCharsets

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.google.inject.Injector

import groovy.util.logging.Slf4j

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
        def locale = factory.create Locale.US, StandardCharsets.UTF_8
        assert locale.locale == Locale.US
        assert locale.charset == StandardCharsets.UTF_8
    }

    @Test
    void "serialize"() {
        def localea = factory.create Locale.US, StandardCharsets.UTF_8
        def localeb = reserialize localea
        assert localea == localeb
    }

    @Test
    void "equal"() {
        def localea = factory.create Locale.US, StandardCharsets.UTF_8
        def localeb = factory.create Locale.US, StandardCharsets.UTF_8
        assert localea == localeb
    }

    @Test
    void "not equal on locale"() {
        def localea = factory.create Locale.US, StandardCharsets.UTF_8
        def localeb = factory.create Locale.GERMAN, StandardCharsets.UTF_8
        assert localea != localeb
    }

    @Test
    void "not equal on charset"() {
        def localea = factory.create Locale.US, StandardCharsets.UTF_8
        def localeb = factory.create Locale.US, StandardCharsets.ISO_8859_1
        assert localea != localeb
    }

    static Injector injector

    static PosixLocaleFactory factory

    @BeforeAll
    static void createFactory() {
        toStringStyle
        this.injector = createInjector new PosixLocaleModule()
        this.factory = injector.getInstance PosixLocaleFactory
    }
}
