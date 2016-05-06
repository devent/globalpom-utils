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
package com.anrisoftware.globalpom.durationformat

import static com.anrisoftware.globalpom.durationformat.DurationFormat.createDurationFormat
import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.junit.Test

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
