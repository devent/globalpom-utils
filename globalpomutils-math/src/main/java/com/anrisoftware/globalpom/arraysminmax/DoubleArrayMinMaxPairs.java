/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.arraysminmax;

import static com.anrisoftware.globalpom.arraysminmax.ArraysMinMaxModule.getDoubleArrayMinMaxPairsFactory;
import static org.apache.commons.lang3.Validate.isTrue;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

/**
 * Using the Mehrdad pair search for a {@code double} array.
 *
 * @see <a
 *      href="http://stackoverflow.com/questions/424800/what-is-the-best-way-to-get-the-minimum-or-maximum-value-from-an-array-of-number">What
 *      is the best way to get the minimum or maximum value from an Array of
 *      numbers?</a>
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.4
 */
public class DoubleArrayMinMaxPairs implements ArrayMinMax {

    /**
     * Creates min/max search for a {@code double} array.
     *
     * @param array
     *            the {@code double} array.
     *
     * @param startIndex
     *            the start index of the array.
     *
     * @param endIndex
     *            the end index of the array.
     *
     * @return the {@link DoubleArrayMinMaxPairs}.
     */
    public static DoubleArrayMinMaxPairs createDoubleArrayMinMaxPairs(
            double[] array, int startIndex, int endIndex) {
        return create(array, startIndex, endIndex);
    }

    /**
     * Creates min/max search for a {@code double} array.
     *
     * @param array
     *            the {@code double} array.
     *
     * @param startIndex
     *            the start index of the array.
     *
     * @param endIndex
     *            the end index of the array.
     *
     * @return the {@link DoubleArrayMinMaxPairs}.
     */
    public static DoubleArrayMinMaxPairs create(double[] array, int startIndex,
            int endIndex) {
        return getDoubleArrayMinMaxPairsFactory().create(array, startIndex,
                endIndex);
    }

    private static class MinMaxPair {

        double min;

        double max;

        MinMaxPair(int min, int max) {
            this.min = min;
            this.max = max;
        }

    }

    private final double[] array;

    private final int startIndex;

    private final int endIndex;

    private MinMaxPair minMaxPair;

    /**
     * @see LongArrayMinMaxPairsFactory#create(double[], int, int)
     */
    @Inject
    DoubleArrayMinMaxPairs(@Assisted double[] array,
            @Assisted("startIndex") int startIndex,
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
        double[] array = this.array;
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

        double big, small;
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

    private void checkArray(double[] array, int start, int end) {
        isTrue(start > -1, "Start > -1");
        isTrue(start <= end, "Start <= end");
        isTrue(end > -1, "End > -1");
        isTrue(end <= array.length, "End <= array length");
    }

    private boolean isOdd(int n) {
        return n % 2 != 0;
    }
}
