/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format.durationsimpleformat;

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
