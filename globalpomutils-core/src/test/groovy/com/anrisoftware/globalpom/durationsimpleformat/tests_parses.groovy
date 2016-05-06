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
package com.anrisoftware.globalpom.durationsimpleformat

[
    [input: "64", value: 64],
    [input: "64ms", value: 64],
    [input: "64s", value: (long)64.0 * 1000],
    [input: "64m", value: (long)64.0 * 1000 * 60],
    [input: "64h", value: (long)64.0 * 1000 * 60 * 60],
    [input: "64d", value: (long)64.0 * 1000 * 60 * 60 * 24],
    [input: "64w", value: (long)64.0 * 1000 * 60 * 60 * 24 * 7],
    [input: "64M", value: (long)64.0 * 1000 * 60 * 60 * 24 * 30],
    [input: "64y", value: (long)64.0 * 1000 * 60 * 60 * 24 * 365],
]
