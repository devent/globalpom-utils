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

package com.anrisoftware.globalpom.math.distribution.range;

import static com.anrisoftware.globalpom.math.distribution.range.RangeFactory.MAX;
import static com.anrisoftware.globalpom.math.distribution.range.RangeFactory.MIN;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Holds the lowest and highest possible value of the range.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public class DefaultRange implements Range {

    private final double min;

    private final double max;

    /**
     * @see RangeFactory#create(double, double)
     *
     * @param min the lowest value.
     *
     * @param max the highest value.
     */
    @AssistedInject
    protected DefaultRange(@Assisted(MIN) double min, @Assisted(MAX) double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * @see RangeFactory#create(Range)
     *
     * @param range the {@link Range}
     */
    @AssistedInject
    protected DefaultRange(@Assisted Range range) {
        this.min = range.getMin();
        this.max = range.getMax();
    }

    @Override
    public double getMin() {
        return min;
    }

    @Override
    public double getMax() {
        return max;
    }

    @Override
    public String toString() {
        return String.format("[%f;%f]", getMin(), getMax());
    }
}
