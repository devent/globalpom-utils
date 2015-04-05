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
package com.anrisoftware.globalpom.arraysminmax;

import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create min/max search for an {@code int} array.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface IntArrayMinMaxPairsFactory {

    /**
     * Creates min/max search for an {@code int} array.
     * 
     * @param array
     *            the {@code int} array.
     * 
     * @param startIndex
     *            the start index of the array.
     * 
     * @param endIndex
     *            the end index of the array.
     * 
     * @return the {@link IntArrayMinMaxPairs}.
     */
    IntArrayMinMaxPairs create(int[] array,
            @Assisted("startIndex") int startIndex,
            @Assisted("endIndex") int endIndex);
}
