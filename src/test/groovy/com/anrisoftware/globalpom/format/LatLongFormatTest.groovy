/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format

import static com.anrisoftware.globalpom.format.latlong.LatitudeFormat.*
import static com.anrisoftware.globalpom.format.latlong.LongitudeFormat.*
import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.measure.unit.NonSI.*
import groovy.util.logging.Slf4j

import org.jscience.geography.coordinates.LatLong
import org.junit.Test

/**
 * @see LongitudeFormat
 * @see LatitudeFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class LatLongFormatTest {

    static formats = [
        [latitude: "40°11'15\" N", longitude: "51°11'6\" E", value: LatLong.valueOf(40.1875d, 51.185d, DEGREE_ANGLE)],
        [latitude: "40°11'15.072\" N", longitude: "51°11'15.072\" E", value: LatLong.valueOf(40.18752d, 51.18752d, DEGREE_ANGLE)],
        [latitude: "40°6' N", longitude: "51°6' E", value: LatLong.valueOf(40.1d, 51.1d, DEGREE_ANGLE)],
        [latitude: "40°6' S", longitude: "51°6' W", value: LatLong.valueOf(-40.1d, -51.1d, DEGREE_ANGLE)],
    ]

    static parses = [
        [latitude: "40°11'15\"", longitude: "51°11'15\"", value: LatLong.valueOf(40.1875d, 51.1875d, DEGREE_ANGLE), decimal: 4],
        [latitude: "40°11'15.13\"", longitude: "51°11'15\"", value: LatLong.valueOf(40.18753611d, 51.1875d, DEGREE_ANGLE), decimal: 8],
        [latitude: "40°12'", longitude: "51°11'15\"", value: LatLong.valueOf(40.1875d, 51.1875d, DEGREE_ANGLE), decimal: 1],
        [latitude: "40°11'15\" N", longitude: "51°11'15\" E", value: LatLong.valueOf(40.1875d, 51.1875d, DEGREE_ANGLE), decimal: 4],
        [latitude: "40°11'15\" S", longitude: "51°11'15\" W", value: LatLong.valueOf(-40.1875d, -51.1875d, DEGREE_ANGLE), decimal: 4],
    ]

    @Test
    void "format latitude"() {
        formats.each {
            def format = createLatitudeFormat it.value
            def str = format.format it.value
            log.info "Format latitude {} as '{}'", it.value, str
            assertStringContent str, it.latitude
        }
    }

    @Test
    void "format longitude"() {
        formats.each {
            def format = createLongitudeFormat it.value
            def str = format.format it.value
            log.info "Format longitude {} as '{}'", it.value, str
            assertStringContent str, it.longitude
        }
    }

    @Test
    void "parse latitude"() {
        parses.each {
            def format = createLatitudeFormat it.value, it.decimal
            def value = format.parse it.latitude
            log.info "Parse latitude '{}' as {}", it.latitude, value
            epsilon = 10**-(it.decimal)
            assertDecimalEquals value.latitudeValue(DEGREE_ANGLE), it.value.latitudeValue(DEGREE_ANGLE)
            assertDecimalEquals value.longitudeValue(DEGREE_ANGLE), it.value.longitudeValue(DEGREE_ANGLE)
        }
    }

    @Test
    void "parse longitude"() {
        parses.each {
            def format = createLongitudeFormat it.value, it.decimal
            def value = format.parse it.longitude
            log.info "Parse longitude '{}' as {}", it.longitude, value
            epsilon = 10**-(it.decimal)
            assertDecimalEquals value.latitudeValue(DEGREE_ANGLE), it.value.latitudeValue(DEGREE_ANGLE)
            assertDecimalEquals value.longitudeValue(DEGREE_ANGLE), it.value.longitudeValue(DEGREE_ANGLE)
        }
    }
}
