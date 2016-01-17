/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-math.
 *
 * globalpomutils-math is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-math is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-math. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.math

import groovy.util.logging.Slf4j

import java.text.DecimalFormat

import org.junit.Test

/**
 * @see MathUtils#sigPlaces(String, char, String)
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
@Slf4j
class SigPlacesTest {

    static data = [
        [value: "4", sig: 1],
        [value: "1.3", sig: 2],
        [value: "4325.334", sig: 7],
        [value: "109", sig: 3],
        [value: "3.005", sig: 4],
        [value: "40.001", sig: 5],
        [value: "0.10", sig: 2],
        [value: "0.0010", sig: 2],
        [value: "3.20", sig: 3],
        [value: "320", sig: 2],
        [value: "14.3000", sig: 6],
        [value: "400.00", sig: 5],
        [value: "2.00E7", sig: 3],
        [value: "1.500E-2", sig: 4],
    ]

    @Test
    void "significant places"() {
        def sep = new DecimalFormat().decimalFormatSymbols.decimalSeparator
        def exsep = new DecimalFormat().decimalFormatSymbols.exponentialSeparator
        data.each {
            log.info "{}", it.value
            int result = MathUtils.sigPlaces(it.value, sep, exsep)
            assert result == it.sig
        }
    }
}
