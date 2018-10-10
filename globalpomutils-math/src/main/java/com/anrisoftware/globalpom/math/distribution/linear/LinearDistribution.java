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
package com.anrisoftware.globalpom.math.distribution.linear;

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

import javax.inject.Inject;

import com.anrisoftware.globalpom.math.distribution.core.AbstractDistribution;
import com.anrisoftware.globalpom.math.distribution.range.DistributionRange;
import com.anrisoftware.globalpom.math.distribution.range.Range;
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
