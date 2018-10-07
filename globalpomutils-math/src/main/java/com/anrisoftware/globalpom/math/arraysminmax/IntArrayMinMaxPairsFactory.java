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
package com.anrisoftware.globalpom.math.arraysminmax;

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

import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create min/max search for {@code int} array.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
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
