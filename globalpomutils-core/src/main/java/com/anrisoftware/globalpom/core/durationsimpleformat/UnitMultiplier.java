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
package com.anrisoftware.globalpom.core.durationsimpleformat;

/*-
 * #%L
 * Global POM Utilities :: Core
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

/**
 * Duration unit multiplier.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.5
 */
public enum UnitMultiplier {

    /**
     * Milliseconds.
     */
    MILLISECONDS("ms", 1.0),

    /**
     * Seconds.
     */
    SECONDS("s", 1000.0),

    /**
     * Minutes.
     */
    MINUTES("m", 1000.0 * 60),

    /**
     * Hours.
     */
    HOURS("h", 1000.0 * 60 * 60),

    /**
     * Days, 24 hours.
     */
    DAYS("d", 1000.0 * 60 * 60 * 24),

    /**
     * Weeks, 7 days.
     */
    WEEKS("w", 1000.0 * 60 * 60 * 24 * 7),

    /**
     * Months, 30 days.
     */
    MONTHS("M", 1000.0 * 60 * 60 * 24 * 30),

    /**
     * Years, 365 days.
     */
    YEARS("y", 1000.0 * 60 * 60 * 24 * 365);

    /**
     * Parses the string to a unit multiplier.
     *
     * @param string
     *            the {@link String}.
     *
     * @return the {@link UnitMultiplier} or {@code null}.
     */
    public static UnitMultiplier parseUnitMultiplier(String string) {
        for (UnitMultiplier value : values()) {
            if (value.getMetric().equals(string)) {
                return value;
            }
        }
        return null;
    }

    private String metric;

    private double value;

    private UnitMultiplier(String metric, double value) {
        this.metric = metric;
        this.value = value;
    }

    public String getMetric() {
        return metric;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return metric;
    }

}
