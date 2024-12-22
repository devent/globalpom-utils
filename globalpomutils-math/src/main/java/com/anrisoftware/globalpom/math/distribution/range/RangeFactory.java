/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.distribution.range;

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
