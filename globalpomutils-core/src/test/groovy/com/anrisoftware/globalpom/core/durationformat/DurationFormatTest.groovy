package com.anrisoftware.globalpom.core.durationformat

import static com.anrisoftware.globalpom.core.durationformat.DurationFormat.createDurationFormat
import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.Test

import groovy.util.logging.Slf4j


/**
 * @see DurationFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
@Slf4j
class DurationFormatTest {

    @Test
    void "format duration"() {
        def format = createDurationFormat()
        new tests_formats().run().each {
            log.info "Format '{}'", it.duration
            def str = format.format it.duration
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse duration"() {
        def format = createDurationFormat()
        new tests_parses().run().each {
            log.info "Parse '{}'", it.input
            def duration = format.parse it.input
            assert duration.millis == it.duration
        }
    }
}
