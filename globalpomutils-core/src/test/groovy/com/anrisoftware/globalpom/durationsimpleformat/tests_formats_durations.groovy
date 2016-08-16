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

import org.joda.time.Duration

[
    [format: "64ms", value: Duration.millis(64), multiplier: MILLISECONDS],
    [format: "64s", value: Duration.standardSeconds(64), multiplier: SECONDS],
    [format: "64m", value: Duration.standardMinutes(64), multiplier: MINUTES],
    [format: "64h", value: Duration.standardHours(64), multiplier: HOURS],
    [format: "65d", value: Duration.standardDays(65), multiplier: DAYS],
]
