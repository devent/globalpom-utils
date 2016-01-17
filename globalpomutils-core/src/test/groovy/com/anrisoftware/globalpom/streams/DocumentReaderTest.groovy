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
package com.anrisoftware.globalpom.streams

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.swing.text.PlainDocument

import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.junit.Test

@CompileStatic
@Slf4j
class DocumentReaderTest {

    @Test
    void "read document"() {
        def document = new PlainDocument()
        document.insertString 0, StringUtils.repeat('text', ' ', 5), null
        document.insertString 0, 'Start>>>', null
        document.insertString document.length, '<<<End', null
        def reader = new DocumentReader(document)
        char[] buff = new char[20]
        int r = 0
        def expected = new StringBuilder()
        while (true) {
            r = reader.read(buff, 0, 20)
            if (r == -1) {
                break
            }
            expected.append buff
        }
        log.info 'Read \'{}\'', expected
        assert expected.toString() == 'Start>>>text text text text text<<<Endte'
    }

    @Test
    void "read document to string"() {
        def document = new PlainDocument()
        document.insertString 0, StringUtils.repeat('text', ' ', 5), null
        document.insertString 0, 'Start>>>', null
        document.insertString document.length, '<<<End', null
        def reader = new DocumentReader(document)
        def expected = IOUtils.toString reader
        log.info 'Read \'{}\'', expected
        assert expected == 'Start>>>text text text text text<<<End'
    }
}
