/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.core.awtserializable

import static com.anrisoftware.globalpom.core.awtserializable.SerializableBasicStroke.decorateSerializableBasicStroke
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
