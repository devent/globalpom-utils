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
    [input: "1230.0(5)", expected: valueFactory.create(12300, 4, 5, -1, 0.5d)],
    [input: "1030.0(5)", expected: valueFactory.create(10300, 4, 5, -1, 0.5d)],
    [input: "123.0(5)", expected: valueFactory.create(1230, 3, 4, -1, 0.5d)],
    [input: "1.0(5)", expected: valueFactory.create(10, 1, 2, -1, 0.5d)],
    [input: "0.123(5)E5", expected: valueFactory.create(123, 5, 3, 2, 0.005e5d)],
    // 5
    [input: "1.23(5)", expected: valueFactory.create(123, 1, 3, -2, 0.05d)],
    [input: "0.123(5)", expected: valueFactory.create(123, 0, 3, -3, 0.005d)],
    [input: "0.0123(5)", expected: valueFactory.create(123, -1, 3, -4, 0.0005d)],
    [input: "0.00123(5)", expected: valueFactory.create(123, -2, 3, -5, 0.00005d)],
    [input: "0.123(5)E-2", expected: valueFactory.create(123, -2, 3, -5, 0.005e-2d)],
    // 10
    [input: "12.123(5)E-2", expected: valueFactory.create(12123, 0, 5, -5, 0.005e-2d)],
    [input: "12.0123(5)E-2", expected: valueFactory.create(120123, 0, 6, -6, 0.0005e-2d)],
    [input: "6.62606957(29)E-34", expected: valueFactory.create(662606957, -33, 9, -42, 2.9E-41)],
    [input: "2981.0(1.1)", expected: valueFactory.create(29810, 4, 5, -1, 1.1d)],
    [input: "547.853(24)", expected: valueFactory.create(547853, 3, 6, -3, 0.024d)],
    [input: "547.853(24)e3", expected: valueFactory.create(547853, 6, 6, 0, 0.024e3d)],
]
