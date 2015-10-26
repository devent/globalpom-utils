/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
    [input: "1230(1%)", expected: valueFactory.create(123, 4, 3, 1, 0.5d)],
    [input: "1030(1%)", expected: valueFactory.create(103, 4, 3, 1, 0.5d)],
    [input: "123(1%)", expected: valueFactory.create(123, 3, 3, 0, 0.5d)],
    [input: "1(1%)", expected: valueFactory.create(1, 1, 1, 0, 0.5d)],
    [input: "0.123E2(1%)", expected: valueFactory.create(123, 2, 3, -1, 0.5d)],
    [input: "1.23(1%)", expected: valueFactory.create(123, 1, 3, -2, 0.5d)],
    [input: "0.123(1%)", expected: valueFactory.create(123, 0, 3, -3, 0.5d)],
    [input: "0.0123(1%)", expected: valueFactory.create(123, -1, 3, -4, 0.5d)],
    [input: "0.00123(1%)", expected: valueFactory.create(123, -2, 3, -5, 0.5d)],
    [input: "0.123E-2(1%)", expected: valueFactory.create(123, -2, 3, -5, 0.5d)],
    [input: "12.123E-2(1%)", expected: valueFactory.create(12123, 0, 5, -5, 0.5d)],
    [input: "12.0123E-2(1%)", expected: valueFactory.create(120123, 0, 6, -6, 0.5d)],
]
