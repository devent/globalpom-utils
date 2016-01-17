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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.FastMath;

import com.anrisoftware.globalpom.distribution.api.Distribution;
import com.anrisoftware.globalpom.distribution.range.DistributionRange;
import com.anrisoftware.globalpom.distribution.range.Range;

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
