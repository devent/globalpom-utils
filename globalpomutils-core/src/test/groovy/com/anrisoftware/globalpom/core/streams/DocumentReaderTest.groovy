/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.core.streams

import javax.swing.text.PlainDocument

import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Test

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

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
