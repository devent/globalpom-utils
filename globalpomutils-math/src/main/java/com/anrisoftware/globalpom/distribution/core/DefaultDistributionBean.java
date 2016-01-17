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

import javax.inject.Inject;

import com.anrisoftware.globalpom.distribution.api.Distribution;
import com.anrisoftware.globalpom.distribution.api.DistributionBean;
import com.anrisoftware.globalpom.distribution.range.Range;
import com.google.inject.assistedinject.Assisted;

/**
 * Distribution of values that informs property change listeners.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public class DefaultDistributionBean implements DistributionBean {

    private final Distribution distribution;

    /**
     * @see DistributionBeanFactory#create(Distribution)
     */
    @Inject
    DefaultDistributionBean(@Assisted Distribution distribution) {
        this.distribution = distribution;
    }

    @Override
    public int getSize() {
        return distribution.getSize();
    }

    @Override
    public boolean add(double value) {
        return distribution.add(value);
    }

    @Override
    public int indexOf(double value) {
        return distribution.indexOf(value);
    }

    @Override
    public long countOf(double value) {
        return distribution.countOf(value);
    }

    @Override
    public TDoubleList values(int index) {
        return distribution.values(index);
    }

    @Override
    public int valuesSize(int index) {
        return distribution.valuesSize(index);
    }

    @Override
    public Range range(int index) {
        return distribution.range(index);
    }

    @Override
    public int getMax() {
        return distribution.getMax();
    }

    @Override
    public void reset() {
        distribution.reset();
    }

    @Override
    public Range getRange() {
        return distribution.getRange();
    }

    @Override
    public double getRangeMin() {
        return distribution.getRangeMin();
    }

    @Override
    public double getRangeMax() {
        return distribution.getRangeMax();
    }

}
