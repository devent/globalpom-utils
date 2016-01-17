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

import javax.inject.Inject;

import com.anrisoftware.globalpom.distribution.core.AbstractDistribution;
import com.anrisoftware.globalpom.distribution.range.DistributionRange;
import com.anrisoftware.globalpom.distribution.range.Range;
import com.google.inject.assistedinject.Assisted;

/**
 * Distribution that have no transformation of the values.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public class LinearDistribution extends AbstractDistribution {

    @Inject
    private LinearDistributionRangeFactory distributionRangeFactory;

    /**
     * For serialization.
     */
    LinearDistribution() {
        this(null, 0);
    }

    /**
     * @see LinearDistributionFactory#create(Range, int)
     */
    @Inject
    LinearDistribution(@Assisted Range range, @Assisted int bins) {
        super(range, bins);
    }

    @Override
    protected DistributionRange distributionRange(Range range) {
        return distributionRangeFactory.create(range);
    }

}
