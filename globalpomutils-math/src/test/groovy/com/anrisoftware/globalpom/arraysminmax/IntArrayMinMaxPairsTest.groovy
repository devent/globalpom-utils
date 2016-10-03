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
package com.anrisoftware.globalpom.arraysminmax

import static com.anrisoftware.globalpom.arraysminmax.IntArrayMinMaxPairs.*

import org.junit.Test

/**
 * @see IntArrayMinMaxPairs
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
class IntArrayMinMaxPairsTest {

    @Test
    void "search min max, empty array"() {
        int[] array = []
        def minmax = createIntArrayMinMaxPairs(array, 0, 0)()
    }

    @Test(expected = IllegalArgumentException)
    void "search min max, empty array, out-of bounds"() {
        int[] array = []
        def minmax = createIntArrayMinMaxPairs(array, 0, 1)()
    }

    @Test
    void "search min max, 1 value"() {
        int[] array = [3]
        def minmax = createIntArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 3
        assert minmax.max == 3
    }

    @Test
    void "search min max, 2 values"() {
        int[] array = [3, 1]
        def minmax = createIntArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 1
        assert minmax.max == 3
    }

    @Test
    void "search min max, even array"() {
        int[] array = [3, 7, 2, 7, 8, 1, 4, 0, 8]
        def minmax = createIntArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 0
        assert minmax.max == 8
    }

    @Test
    void "search min max, odd array"() {
        int[] array = [3, 7, 2, 7, 8, 1, 4, 0]
        def minmax = createIntArrayMinMaxPairs(array, 0, array.length - 1)()
        assert minmax.min == 0
        assert minmax.max == 8
    }
}
