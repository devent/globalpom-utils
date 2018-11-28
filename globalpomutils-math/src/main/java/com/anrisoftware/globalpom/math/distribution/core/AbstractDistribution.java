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

package com.anrisoftware.globalpom.math.distribution.core;

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

import gnu.trove.list.TDoubleList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;

import com.anrisoftware.globalpom.math.distribution.api.Distribution;
import com.anrisoftware.globalpom.math.distribution.range.DistributionRange;
import com.anrisoftware.globalpom.math.distribution.range.Range;

/**
 * Implements the property change supports; the distribution range; the
 * distribution size.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public abstract class AbstractDistribution implements Distribution {

    private static final String RANGE = "range";

    private static final String BINS = "bins";

    private final int size;

    private final Range range;

    private final List<DistributionBin> binsTable;

    private AbstractDistributionLogger log;

    private DistributionBinFactory binFactory;

    private int max;

    /**
     * Sets the count of classes for the distribution.
     *
     * @param range
     *            the {@link DistributionRange} range of the valid values of the
     *            distribution.
     *
     * @param bins
     *            the count of classes.
     */
    protected AbstractDistribution(Range range, int bins) {
        this.range = range;
        this.size = bins;
        this.binsTable = new ArrayList<DistributionBin>();
    }

    @Inject
    void setAbstractDistributionLogger(AbstractDistributionLogger logger) {
        this.log = logger;
        log.checkBins(this, size);
    }

    @Inject
    void setDistributionBinFactory(DistributionBinFactory factory) {
        this.binFactory = factory;
    }

    /**
     * Returns the scaled range of this distribution for the specified range.
     *
     * @param range
     *            the {@link Range}.
     *
     * @return the {@link DistributionRange}.
     */
    protected abstract DistributionRange distributionRange(Range range);

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Range getRange() {
        return range;
    }

    @Override
    public double getRangeMin() {
        return range.getMin();
    }

    @Override
    public double getRangeMax() {
        return range.getMax();
    }

    @Override
    public void reset() {
        binsTable.clear();
        int size = getSize();
        int index = 0;
        DistributionRange range = distributionRange(getRange());
        for (DistributionRange binrange : range.iterator(size)) {
            DistributionBin bin = binFactory.create(binrange, index++);
            binsTable.add(bin);
        }
    }

    @Override
    public boolean add(double value) {
        DistributionBin bin = findBin(value);
        boolean res = bin.addValue(value);
        if (res) {
            int oldMax = this.max;
            this.max = FastMath.max(oldMax, bin.getCount());
        }
        return res;
    }

    @Override
    public int indexOf(double value) {
        int size = getSize();
        double min = getRangeMin();
        double step = (getRangeMax() - getRangeMin()) / size;
        int index = (int) FastMath.floor((value - min) / step);
        return FastMath.min(size - 1, FastMath.max(0, index));
    }

    @Override
    public long countOf(double value) {
        DistributionBin bin = findBin(value);
        return bin == null ? 0 : valuesSize(bin.getIndex());
    }

    /**
     * Returns the distribution bin for the value.
     *
     * @param value
     *            the value.
     *
     * @return the {@link DistributionBin}.
     */
    public DistributionBin findBin(double value) {
        int index = indexOf(value);
        List<DistributionBin> bins = getBins();
        return bins.get(index);
    }

    /**
     * Returns the bins of the distribution.
     *
     * @return the {@link List} of the {@link DistributionBin} bins.
     */
    public List<DistributionBin> getBins() {
        return binsTable;
    }

    @Override
    public TDoubleList values(int index) {
        return binsTable.get(index).getValues();
    }

    @Override
    public int valuesSize(int index) {
        return binsTable.get(index).getCount();
    }

    @Override
    public Range range(int index) {
        return binsTable.get(index);
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(BINS, size)
                .append(RANGE, range).toString();
    }

}
