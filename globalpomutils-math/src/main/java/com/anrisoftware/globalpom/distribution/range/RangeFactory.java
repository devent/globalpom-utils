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
package com.anrisoftware.globalpom.distribution.range;

import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create a range of values.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface RangeFactory {

    public static final String MAX = "max";
    public static final String MIN = "min";

    /**
     * Creates the range from the lowest to the highest possible value.
     *
     * @param min
     *            the lowest possible value that the distribution can contain.
     *
     * @param max
     *            the highest possible value that the distribution can contain.
     *
     * @return the {@link Range}.
     */
    Range create(@Assisted(MIN) double min, @Assisted(MAX) double max);

    /**
     * Creates the range from the lowest to the highest possible value of the
     * specified range.
     *
     * @param range
     *            the {@link Range}.
     *
     * @return the {@link Range}.
     */
    Range create(Range range);
}
