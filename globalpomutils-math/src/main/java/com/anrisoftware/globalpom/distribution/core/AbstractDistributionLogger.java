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
package com.anrisoftware.globalpom.distribution.core;

import static com.anrisoftware.globalpom.distribution.core.AbstractDistributionLogger._.bins_less;
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
