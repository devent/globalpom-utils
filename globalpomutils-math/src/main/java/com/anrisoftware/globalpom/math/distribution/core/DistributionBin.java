/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.distribution.core;

import gnu.trove.list.TDoubleList;
import gnu.trove.list.array.TDoubleArrayList;

import jakarta.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.math.distribution.range.DistributionRange;
import com.google.inject.assistedinject.Assisted;

/**
 * Adds to the distribution/bin a position index.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public class DistributionBin implements DistributionRange {

    private static final String INDEX_FIELD = "index";

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
        return new ToStringBuilder(this).appendToString(range.toString()).append(INDEX_FIELD, index).toString();
    }

}
