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

import static com.anrisoftware.globalpom.math.distribution.core.AbstractDistributionLogger._.bins_less;
import static org.apache.commons.lang3.Validate.isTrue;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link AbstractDistribution}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
@Singleton
class AbstractDistributionLogger extends AbstractLogger {

    private static final int MIN_BINS = 1;

    enum _ {

        bins_less("Count of classes %d must be >%d for %s.");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Creates a logger for {@link AbstractDistribution}.
     */
    public AbstractDistributionLogger() {
        super(AbstractDistribution.class);
    }

    void checkBins(AbstractDistribution distribution, int bins) {
        isTrue(bins > MIN_BINS, bins_less.toString(), bins, MIN_BINS,
                distribution);
    }

}
