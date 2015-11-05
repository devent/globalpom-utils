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
package com.anrisoftware.globalpom.distribution.api;

import com.anrisoftware.globalpom.distribution.range.Range;

/**
 * Factory to create distribution of values.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface DistributionFactory {

    /**
     * Creates distribution of the values with the specified the count of
     * classes.
     *
     * @param range
     *            the {@link Range} range of the valid values of the
     *            distribution.
     *
     * @param bins
     *            the count of classes.
     *
     * @return the {@link Distribution}.
     */
    Distribution create(Range range, int bins);
}
