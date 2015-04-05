/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.resources

import org.junit.Test

/**
 * @see StringToURI
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class StringToURITest {

    @Test
    void "convert to URI"() {
        inputs.each {
            assert StringToURI.toURI(it.path) == it.uri
        }
    }

    static inputs = [
        [path: "file.txt", uri: new URI("file://file.txt")],
        [path: "file://file.txt", uri: new URI("file://file.txt")],
    ]
}
