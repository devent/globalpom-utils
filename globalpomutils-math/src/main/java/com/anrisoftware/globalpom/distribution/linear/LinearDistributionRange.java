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
package com.anrisoftware.globalpom.distribution.linear;

import static org.apache.commons.math3.util.FastMath.abs;

import java.util.Iterator;

import javax.inject.Inject;

import com.anrisoftware.globalpom.distribution.range.DefaultRange;
import com.anrisoftware.globalpom.distribution.range.DistributionRange;
import com.anrisoftware.globalpom.distribution.range.Range;
import com.anrisoftware.globalpom.distribution.range.RangeFactory;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Linear distribution range.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
class LinearDistributionRange extends DefaultRange implements DistributionRange {

    @Inject
    private RangeFactory rangeFactory;

    @Inject
    private LinearDistributionRangeFactory linearRangeFactory;

    /**
     * @see LinearDistributionRangeFactory#create(DistributionRange)
     */
    @AssistedInject
    LinearDistributionRange(@Assisted Range range) {
        super(range);
    }

    @Override
    public Iterable<DistributionRange> iterator(final int size) {
        return new Iterable<DistributionRange>() {

            @Override
            public Iterator<DistributionRange> iterator() {
                return new It(size);
            }
        };
    }

    private class It implements Iterator<DistributionRange> {

        private final int size;

        private int index;

        It(int size) {
            this.size = size;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public DistributionRange next() {
            return nextRange(index++, size, getMax());
        }

        private DistributionRange nextRange(int index, int size, double max) {
            double min = getMin();
            double step = abs((min - max) / size);
            double rmin = min + step * index;
            double rmax = min + step * (index + 1);
            return linearRangeFactory.create(rangeFactory.create(rmin, rmax));
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
