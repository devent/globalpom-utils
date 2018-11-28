/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

package com.anrisoftware.globalpom.math.distribution.api;

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

import com.anrisoftware.globalpom.math.distribution.range.Range;

import gnu.trove.list.TDoubleList;

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
