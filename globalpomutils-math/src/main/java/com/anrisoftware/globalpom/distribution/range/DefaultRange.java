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
package com.anrisoftware.globalpom.distribution.range;

import static com.anrisoftware.globalpom.distribution.range.RangeFactory.MAX;
import static com.anrisoftware.globalpom.distribution.range.RangeFactory.MIN;

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
     */
    @AssistedInject
    protected DefaultRange(@Assisted(MIN) double min, @Assisted(MAX) double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * @see RangeFactory#create(Range)
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
