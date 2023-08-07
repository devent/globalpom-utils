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
package com.anrisoftware.globalpom.math.arraysminmax;

import static com.anrisoftware.globalpom.math.arraysminmax.ArraysMinMaxModule.getIntArrayMinMaxPairsFactory;
import static org.apache.commons.lang3.Validate.isTrue;

import jakarta.inject.Inject;

import com.google.inject.assistedinject.Assisted;

/**
 * Using the Mehrdad pair search for an {@code int} array.
 *
 * @see <a href=
 *      "http://stackoverflow.com/questions/424800/what-is-the-best-way-to-get-the-minimum-or-maximum-value-from-an-array-of-number">What
 *      is the best way to get the minimum or maximum value from an Array of
 *      numbers?</a>
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
public class IntArrayMinMaxPairs implements ArrayMinMax {

    /**
     * @see IntArrayMinMaxPairsFactory#create(int[], int, int)
     *
     * @param array      the integer array.
     *
     * @param startIndex the start index.
     *
     * @param endIndex   the end index.
     *
     * @return the {@link IntArrayMinMaxPairs}
     */
    public static IntArrayMinMaxPairs createIntArrayMinMaxPairs(int[] array, int startIndex, int endIndex) {
        return create(array, startIndex, endIndex);
    }

    /**
     * @see IntArrayMinMaxPairsFactory#create(int[], int, int)
     *
     * @param array      the integer array.
     *
     * @param startIndex the start index.
     *
     * @param endIndex   the end index.
     *
     * @return the {@link IntArrayMinMaxPairs}
     */
    public static IntArrayMinMaxPairs create(int[] array, int startIndex, int endIndex) {
        return getIntArrayMinMaxPairsFactory().create(array, startIndex, endIndex);
    }

    private static class MinMaxPair {

        int min;

        int max;

        MinMaxPair(int min, int max) {
            this.min = min;
            this.max = max;
        }

    }

    private final int[] array;

    private final int startIndex;

    private final int endIndex;

    private MinMaxPair minMaxPair;

    /**
     * @see IntArrayMinMaxPairsFactory#create(int[], int, int)
     */
    @Inject
    IntArrayMinMaxPairs(@Assisted int[] array, @Assisted("startIndex") int startIndex,
            @Assisted("endIndex") int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public Number getMin() {
        return minMaxPair.min;
    }

    @Override
    public Number getMax() {
        return minMaxPair.max;
    }

    @Override
    public ArrayMinMax call() {
        MinMaxPair minmax = new MinMaxPair(0, 0);
        checkArray(array, startIndex, endIndex);
        if (array.length > 0) {
            minmax = findMinMax(minmax);
        }
        this.minMaxPair = minmax;
        return this;
    }

    private MinMaxPair findMinMax(MinMaxPair minmax) {
        int[] array = this.array;
        int end = endIndex, start = startIndex, index = 0;
        // n: the number of elements to be sorted, assuming n>0
        int n = end - start + 1;
        if (isOdd(n)) {
            minmax.min = array[start];
            minmax.max = array[start];
            index = start + 1;
        } else if (array[start] < array[start + 1]) {
            minmax.min = array[start];
            minmax.max = array[start + 1];
            index = start + 2;
        } else {
            minmax.min = array[start + 1];
            minmax.max = array[start];
            index = start + 2;
        }

        int big, small;
        for (int i = index; i < n - 1; i = i + 2) {
            if (array[i] < array[i + 1]) {
                small = array[i];
                big = array[i + 1];
            } else {
                small = array[i + 1];
                big = array[i];
            }
            if (minmax.min > small) {
                minmax.min = small;
            }
            if (minmax.max < big) {
                minmax.max = big;
            }
        }
        return minmax;
    }

    private void checkArray(int[] array, int start, int end) {
        isTrue(start > -1, "Start > -1");
        isTrue(start <= end, "Start <= end");
        isTrue(end > -1, "End > -1");
        isTrue(end <= array.length, "End <= array length");
    }

    private boolean isOdd(int n) {
        return n % 2 != 0;
    }
}
