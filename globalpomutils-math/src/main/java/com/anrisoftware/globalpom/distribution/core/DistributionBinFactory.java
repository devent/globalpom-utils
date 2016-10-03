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
package com.anrisoftware.globalpom.distribution.core;

import com.anrisoftware.globalpom.distribution.range.DistributionRange;

/**
 * Factory to create the distribution bin.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public interface DistributionBinFactory {

    /**
     * Creates the distribution bin.
     *
     * @param range
     *            the lowest and highest possible value that the distribution
     *            can contain.
     *
     * @param index
     *            the bin position index.
     *
     * @return the {@link DistributionBin}.
     */
    DistributionBin create(DistributionRange range, int index);
}
