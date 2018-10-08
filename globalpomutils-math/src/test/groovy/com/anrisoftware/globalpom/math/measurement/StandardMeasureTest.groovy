/*-
 * #%L
 * Global POM Utilities :: Math
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.anrisoftware.globalpom.math.measurement

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static org.apache.commons.math3.util.FastMath.*

import javax.measure.unit.SI

import org.junit.jupiter.api.Test

import com.thoughtworks.xstream.XStream

import groovy.util.logging.Slf4j

/**
 * @see StandardMeasure
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class StandardMeasureTest extends ValueTestBase {

    @Test
    void "serialize"() {
        def value = standardValueFactory.create(123, 4, 3, 1)
        def measure = standardMeasureFactory.create value, SI.METER
        def measureB = standardMeasureFactory.create reserialize(measure)
        assertDecimalEquals measureB.getValue(), 1230
        def v = measureB.mul standardValueFactory.create(1, 1, 1, 0)
    }

    @Test
    void "xstream, serialize"() {
        def xstream = new XStream()
        def value = standardValueFactory.create(123, 4, 3, 1)
        def measure = standardMeasureFactory.create value, SI.METER
        def xml = xstream.toXML measure
        def measureB = standardMeasureFactory.create xstream.fromXML(xml)
        assertDecimalEquals measureB.getValue(), 1230
        def v = measureB.mul standardValueFactory.create(1, 1, 1, 0)
    }
}