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
package com.anrisoftware.globalpom.durationformat

[
    [input: "P10Y20M30DT4H5M6.7S", duration: 365486706700l],
    [input: "P20M30DT4H5M6.7S", duration: 54446706700l],
    [input: "PT4H5M6.7S", duration: 14706700],
    [input: "PT4S", duration: 4l*1000l],
    [input: "PT4H", duration: 4l*60l*60l*1000l],
]

