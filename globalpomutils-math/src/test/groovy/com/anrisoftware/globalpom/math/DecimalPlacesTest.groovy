/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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

import java.text.DecimalFormat

import org.junit.Test

/**
 * Test the round to zero algorithm.
 *
 * @see MathUtils#decimalPlaces(String, char)
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
class DecimalPlacesTest {

    static data = [
        [value: "0", result: 0 ],
        [value: "-5.2", result: 1 ],
        [value: "5.2", result: 1 ],
        [value: "-1.47956", result: 5 ],
        [value: "1.47672", result: 5 ],
        [value: "0.1E5", result: 4 ],
        [value: "0.1E-5", result: 6 ],
        [value: "1E5", result: 5 ],
        [value: "1E-5", result: 5 ],
    ]

    @Test
    void "decimal places"() {
        def sep = new DecimalFormat().decimalFormatSymbols.decimalSeparator
        def exsep = new DecimalFormat().decimalFormatSymbols.exponentialSeparator
        data.each {
            int result = MathUtils.decimalPlaces(it.value, sep, exsep)
            assert result == it.result
        }
    }
}
