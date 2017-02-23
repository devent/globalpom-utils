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
package com.anrisoftware.globalpom.math.arraysminmax

import static com.anrisoftware.globalpom.math.arraysminmax.LongArrayMinMaxPairs.*

import org.junit.Test

/**
 * @see LongArrayMinMaxPairs
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
class LongArrayMinMaxPairsTest {

    @Test
    void "search min max, empty array"() {
        long[] array = []
        def minmax = createLongArrayMinMaxPairs(array, 0, 0)()
    }

    @Test(expected = IllegalArgumentException)
    void "search min max, empty array, out-of bounds"() {
        long[] array = []
        def minmax = createLongArrayMinMaxPairs(array, 0, 1)()
    }

    @Test
    void "search min max, 1 value"() {
        long[] array = [3]
        def minmax = createLongArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 3
        assert minmax.max == 3
    }

    @Test
    void "search min max, 2 values"() {
        long[] array = [3, 1]
        def minmax = createLongArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 1
        assert minmax.max == 3
    }

    @Test
    void "search min max, 2 values (2)"() {
        long[] array = [1, 2]
        def minmax = createLongArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 1
        assert minmax.max == 2
    }

    @Test
    void "search min max, even array"() {
        long[] array = [3, 7, 2, 7, 8, 1, 4, 0, 8]
        def minmax = createLongArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 0
        assert minmax.max == 8
    }

    @Test
    void "search min max, odd array"() {
        long[] array = [3, 7, 2, 7, 8, 1, 4, 0]
        def minmax = createLongArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 0
        assert minmax.max == 8
    }
}
