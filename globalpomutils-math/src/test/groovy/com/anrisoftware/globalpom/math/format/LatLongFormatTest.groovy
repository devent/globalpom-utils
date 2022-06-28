/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.format

import static com.anrisoftware.globalpom.math.format.latlong.LatitudeFormat.*
import static com.anrisoftware.globalpom.math.format.latlong.LongitudeFormat.*
import static com.anrisoftware.globalpom.utils.TestUtils.*
import static javax.measure.unit.NonSI.*

import org.jscience.geography.coordinates.LatLong
import org.junit.jupiter.api.Test

import groovy.util.logging.Slf4j

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
