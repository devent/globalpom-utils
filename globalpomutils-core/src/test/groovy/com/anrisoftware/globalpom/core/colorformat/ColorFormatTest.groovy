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
