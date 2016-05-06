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
package com.anrisoftware.globalpom.posixlocale

import org.apache.commons.io.Charsets

[
    [input: localeFactory.create(Locale.GERMAN, Charsets.UTF_8), expected: "de.UTF-8"],
    [input: localeFactory.create(Locale.US, Charsets.UTF_8), expected: "en_US.UTF-8"],
    [input: localeFactory.create(Locale.US, Charsets.ISO_8859_1), expected: "en_US.ISO-8859-1"],
]
