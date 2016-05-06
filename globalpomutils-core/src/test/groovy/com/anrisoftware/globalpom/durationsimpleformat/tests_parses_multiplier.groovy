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

import static com.anrisoftware.globalpom.durationsimpleformat.UnitMultiplier.*

[
    [input: "64ms", value: 64, multiplier: MILLISECONDS],
    [input: "64s", value: 64, multiplier: SECONDS],
    [input: "64m", value: 64, multiplier: MINUTES],
    [input: "64h", value: 64, multiplier: HOURS],
    [input: "64d", value: 64, multiplier: DAYS],
    [input: "64w", value: 64, multiplier: WEEKS],
    [input: "64M", value: 64, multiplier: MONTHS],
    [input: "64y", value: 64, multiplier: YEARS],
]
