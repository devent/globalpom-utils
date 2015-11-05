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
package com.anrisoftware.globalpom.distribution.api;

import gnu.trove.list.TDoubleList;

import com.anrisoftware.globalpom.distribution.range.Range;

/**
 * Distribution of values.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface Distribution {

    /**
     * Indicating no index was found.
     *
     * @see #indexOf(DistributionValue)
     */
    static final int NONE = -1;

    /**
     * Returns the count of classes for the distribution.
     *
     * @return the count of classes.
     */
    int getSize();

    /**
     * Adds the value to the distribution.
     *
     * @param value
     *            the value to add.
     *
     * @return {@code true} if the value was added to the distribution.
     */
    boolean add(double value);

    /**
     * Returns the distribution index of the specified value.
     *
     * @param value
     *            the value.
     *
     * @return the index.
     */
    int indexOf(double value);

    /**
     * Returns the frequency of the specified value.
     *
     * @param value
     *            the value.
     *
     * @return the frequency of the value in the distribution or 0 if the value
     *         is not in the distribution.
     */
    long countOf(double value);

    /**
     * Returns the values of the distribution class with the specified index.
     *
     * @param index
     *            the class index.
     *
     * @return the {@link TDoubleList} with the values.
     */
    TDoubleList values(int index);

    /**
     * Returns the count of values of the distribution class with the specified
     * index.
     *
     * @param index
     *            the class index.
     *
     * @return the values count.
     */
    int valuesSize(int index);

    /**
     * Returns the range of the class with the specified index.
     *
     * @param index
     *            the class index.
     *
     * @return the {@link Range}.
     */
    Range range(int index);

    /**
     * Returns the maximal count of values of the distribution.
     *
     * @return the maximal count.
     */
    int getMax();

    /**
     * Resets the distribution.
     */
    void reset();

    /**
     * Returns the range of the valid values of the distribution.
     *
     * @return the {@link Range}.
     *
     * @see #getRangeMin()
     * @see #getRangeMax()
     */
    Range getRange();

    /**
     * Returns the lowest possible value that the distribution can contain.
     *
     * @return the range's minimum.
     */
    double getRangeMin();

    /**
     * Returns the highest possible value represented in the histogram.
     *
     * @return the range's maximum.
     */
    double getRangeMax();

}
