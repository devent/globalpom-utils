/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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

import javax.inject.Inject;

import com.anrisoftware.globalpom.math.distribution.api.Distribution;
import com.anrisoftware.globalpom.math.distribution.api.DistributionBean;
import com.anrisoftware.globalpom.math.distribution.range.Range;
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
