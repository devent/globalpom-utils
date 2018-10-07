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
package com.anrisoftware.globalpom.math.arraysminmax

import static com.anrisoftware.globalpom.math.arraysminmax.DoubleArrayMinMaxPairs.*
import static org.junit.jupiter.api.Assertions.assertThrows

import org.junit.jupiter.api.Test

/**
 * @see DoubleArrayMinMaxPairs
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
class DoubleArrayMinMaxPairsTest {

    @Test
    void "search min max, empty array"() {
        double[] array = []
        def minmax = createDoubleArrayMinMaxPairs(array, 0, 0)()
    }

    @Test
    void "search min max, empty array, out-of bounds"() {
        double[] array = []
        assertThrows IllegalArgumentException, {
            def minmax = createDoubleArrayMinMaxPairs(array, 0, 1)()
        }
    }

    @Test
    void "search min max, 1 value"() {
        double[] array = [3]
        def minmax = createDoubleArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 3
        assert minmax.max == 3
    }

    @Test
    void "search min max, 2 values"() {
        double[] array = [3, 1]
        def minmax = createDoubleArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 1
        assert minmax.max == 3
    }

    @Test
    void "search min max, 2 values (2)"() {
        double[] array = [1, 2]
        def minmax = createDoubleArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 1
        assert minmax.max == 2
    }

    @Test
    void "search min max, even array"() {
        double[] array = [3, 7, 2, 7, 8, 1, 4, 0, 8]
        def minmax = createDoubleArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 0
        assert minmax.max == 8
    }

    @Test
    void "search min max, odd array"() {
        double[] array = [3, 7, 2, 7, 8, 1, 4, 0]
        def minmax = createDoubleArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 0
        assert minmax.max == 8
    }
}
