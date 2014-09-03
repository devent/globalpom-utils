/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-math.
 *
 * globalpomutils-math is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-math is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-math. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.arraysminmax

import static com.anrisoftware.globalpom.arraysminmax.LongArrayMinMaxPairs.*

import org.junit.Test

/**
 * @see LongArrayMinMaxPairs
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
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
