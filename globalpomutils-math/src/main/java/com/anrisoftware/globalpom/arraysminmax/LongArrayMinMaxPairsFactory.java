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
package com.anrisoftware.globalpom.arraysminmax;

import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create min/max search for {@code long} array.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
public interface LongArrayMinMaxPairsFactory {

    /**
     * Creates min/max search for an {@code long} array.
     *
     * @param array
     *            the {@code long} array.
     *
     * @param startIndex
     *            the start index of the array.
     *
     * @param endIndex
     *            the end index of the array.
     *
     * @return the {@link LongArrayMinMaxPairs}.
     */
    LongArrayMinMaxPairs create(long[] array,
            @Assisted("startIndex") int startIndex,
            @Assisted("endIndex") int endIndex);
}
