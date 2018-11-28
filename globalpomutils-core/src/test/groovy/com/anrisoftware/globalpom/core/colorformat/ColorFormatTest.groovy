/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

package com.anrisoftware.globalpom.core.colorformat


import static com.anrisoftware.globalpom.core.colorformat.ColorFormat.*
import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.awt.Color

import org.junit.jupiter.api.Test

/**
 * @see ColorFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
class ColorFormatTest {

    static formats = [
        [format: "#ff00ff", color: new Color(255, 0, 255)],
        [format: "#80ff00ff", color: new Color(255, 0, 255, 128)],
    ]

    static outputs = [
        [input: "#ff00ff", color: new Color(255, 0, 255)],
        [input: "#80ff00ff", color: new Color(255, 0, 255, 128)],
    ]

    @Test
    void "format color"() {
        def format = createColorFormat()
        formats.each {
            def str = format.format it.color
            assertStringContent str, it.format
        }
    }

    @Test
    void "parse socket address"() {
        def format = createColorFormat()
        outputs.each {
            def color = format.parse it.input
            assert color == it.color
        }
    }
}
