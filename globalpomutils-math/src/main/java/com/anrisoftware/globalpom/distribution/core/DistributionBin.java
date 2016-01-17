/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.distribution.core;

import gnu.trove.list.TDoubleList;
import gnu.trove.list.array.TDoubleArrayList;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.distribution.range.DistributionRange;
import com.google.inject.assistedinject.Assisted;

/**
 * Adds to the distribution/bin a position index.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public class DistributionBin implements DistributionRange {

    private static final String INDEX = "index";

    private final DistributionRange range;

    private final TDoubleList values;

    private final int index;

    /**
     * @see DistributionBinFactory#create(DistributionRange, int)
     */
    @Inject
    DistributionBin(@Assisted DistributionRange range, @Assisted int index) {
        this.range = range;
        this.values = new TDoubleArrayList();
        this.index = index;
    }

    public boolean addValue(double value) {
        return values.add(value);
    }

    public void removeValue(double value) {
        values.remove(value);
    }

    public TDoubleList getValues() {
        return values;
    }

    public int getCount() {
        return values.size();
    }

    @Override
    public double getMin() {
        return range.getMin();
    }

    @Override
    public double getMax() {
        return range.getMax();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public Iterable<DistributionRange> iterator(int size) {
        return range.iterator(size);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).appendToString(range.toString())
                .append(INDEX, index).toString();
    }

}
