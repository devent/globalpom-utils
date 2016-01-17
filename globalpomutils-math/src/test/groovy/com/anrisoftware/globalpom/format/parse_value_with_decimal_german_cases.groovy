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
    [input: "-1230", expected: valueFactory.create(-1230000, 4, 7, -3)],
    [input: "1230", expected: valueFactory.create(1230000, 4, 7, -3)],
    [input: "1030", expected: valueFactory.create(1030000, 4, 7, -3)],
    [input: "123", expected: valueFactory.create(123000, 3, 6, -3)],
    [input: "10,00", expected: valueFactory.create(10000, 2, 5, -3)],
    [input: "5,6445E2", expected: valueFactory.create(564450, 3, 6, -3)],
    [input: "1", expected: valueFactory.create(1000, 1, 4, -3)],
    [input: "-1", expected: valueFactory.create(-1000, 1, 4, -3)],
    [input: "0", expected: valueFactory.create(0, 0, 1, -1)],
    [input: "-0", expected: valueFactory.create(0, 0, 1, -1)],
    [input: "7E-1", expected: valueFactory.create(700, 0, 3, -3)],
    [input: "0,7", expected: valueFactory.create(700, 0, 3, -3)],
    [input: "0,07", expected: valueFactory.create(70, -1, 2, -3)],
    [input: "0,050000000", expected: valueFactory.create(50, -1, 2, -3)],
    [input: "0,500000000", expected: valueFactory.create(500, 0, 3, -3)],
    [input: "0,005050000", expected: valueFactory.create(5, -2, 1, -3)],
    [input: "0,123E2", expected: valueFactory.create(12300, 2, 5, -3)],
    [input: "-0,123E2", expected: valueFactory.create(-12300, 2, 5, -3)],
    [input: "12,3", expected: valueFactory.create(12300, 2, 5, -3)],
    [input: "15,0", expected: valueFactory.create(15000, 2, 5, -3)],
    [input: "11,02318310912886", expected: valueFactory.create(11023, 2, 5, -3)],
    [input: "1,23", expected: valueFactory.create(1230, 1, 4, -3)],
    [input: "0,123", expected: valueFactory.create(123, 0, 3, -3)],
    [input: "0,0123", expected: valueFactory.create(12, -1, 2, -3)],
    [input: "0,00123", expected: valueFactory.create(1, -2, 1, -3)],
    [input: "0,123E-2", expected: valueFactory.create(1, -2, 1, -3)],
    [input: "12,123E-2", expected: valueFactory.create(121, 0, 3, -3)],
    [input: "12,0123E-2", expected: valueFactory.create(120, 0, 3, -3)],
]
