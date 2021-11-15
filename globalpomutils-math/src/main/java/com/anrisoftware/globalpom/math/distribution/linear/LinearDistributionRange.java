/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.distribution.linear;

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

import static org.apache.commons.math3.util.FastMath.abs;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import com.anrisoftware.globalpom.math.distribution.range.DefaultRange;
import com.anrisoftware.globalpom.math.distribution.range.DistributionRange;
import com.anrisoftware.globalpom.math.distribution.range.Range;
import com.anrisoftware.globalpom.math.distribution.range.RangeFactory;
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
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                return nextRange(index++, size, getMax());
            }
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
