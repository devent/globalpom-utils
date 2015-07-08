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
package com.anrisoftware.globalpom.awtserializable

import static com.anrisoftware.globalpom.awtserializable.SerializableBasicStroke.decorateSerializableBasicStroke
import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.awt.BasicStroke

import org.junit.Test

/**
 * @see SerializableBasicStroke
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.7
 */
class SerializableBasicStrokeTest {

    @Test
    void "serialize basic stroke"() {
        def stroke = new BasicStroke()
        def sstroke = decorateSerializableBasicStroke stroke
        def sstrokeB = reserialize sstroke
        assert sstrokeB == sstroke
    }

    @Test
    void "serialize basic stroke with dash array"() {
        float[] dash = [0.5, 0.3] as float[]
        def stroke = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f)
        def sstroke = decorateSerializableBasicStroke stroke
        def sstrokeB = reserialize sstroke
        assert sstrokeB == sstroke
    }
}
