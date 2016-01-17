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
package com.anrisoftware.globalpom.format

[
    [input: "299792458.000000000", expected: valueFactory.create(299792458000000000, 9, 18, -9)],
    [input: "-1230", expected: valueFactory.create(-123, 4, 3, 1)],
    [input: "1230", expected: valueFactory.create(123, 4, 3, 1)],
    [input: "1030", expected: valueFactory.create(103, 4, 3, 1)],
    [input: "123", expected: valueFactory.create(123, 3, 3, 0)],
    [input: "10.00", expected: valueFactory.create(1000, 2, 4, -2)],
    [input: "5.6445E2", expected: valueFactory.create(56445, 3, 5, -2)],
    [input: "1", expected: valueFactory.create(1, 1, 1, 0)],
    [input: "-1", expected: valueFactory.create(-1, 1, 1, 0)],
    [input: "0", expected: valueFactory.create(0, 0, 1, -1)],
    [input: "-0", expected: valueFactory.create(0, 0, 1, -1)],
    [input: "7E-1", expected: valueFactory.create(7, 0, 1, -1)],
    [input: "0.7", expected: valueFactory.create(7, 0, 1, -1)],
    [input: "0.07", expected: valueFactory.create(7, -1, 1, -2)],
    [input: "0.050000000", expected: valueFactory.create(50000000, -1, 8, -9)],
    [input: "0.500000000", expected: valueFactory.create(500000000, 0, 9, -9)],
    [input: "0.005050000", expected: valueFactory.create(5050000, -2, 7, -9)],
    [input: "0.123E2", expected: valueFactory.create(123, 2, 3, -1)],
    [input: "-0.123E2", expected: valueFactory.create(-123, 2, 3, -1)],
    [input: "12.3", expected: valueFactory.create(123, 2, 3, -1)],
    [input: "15.0", expected: valueFactory.create(150, 2, 3, -1)],
    [input: "11.02318310912886", expected: valueFactory.create(1102318310912886, 2, 16, -14)],
    [input: "1.23", expected: valueFactory.create(123, 1, 3, -2)],
    [input: "0.123", expected: valueFactory.create(123, 0, 3, -3)],
    [input: "0.0123", expected: valueFactory.create(123, -1, 3, -4)],
    [input: "0.00123", expected: valueFactory.create(123, -2, 3, -5)],
    [input: "0.123E-2", expected: valueFactory.create(123, -2, 3, -5)],
    [input: "12.123E-2", expected: valueFactory.create(12123, 0, 5, -5)],
    [input: "12.0123E-2", expected: valueFactory.create(120123, 0, 6, -6)],
    [input: "12.0123e-2", expected: valueFactory.create(120123, 0, 6, -6)],
]
