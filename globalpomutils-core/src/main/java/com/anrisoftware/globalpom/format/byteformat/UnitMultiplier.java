/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.format.byteformat;

import static org.apache.commons.math3.util.FastMath.pow;

/**
 * SI and IEC byte unit multiplier.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public enum UnitMultiplier {

    ONE("", 1),

    KILO("k", pow(1000, 1)),

    MEGA("M", pow(1000, 2)),

    GIGA("G", pow(1000, 3)),

    TERA("T", pow(1000, 4)),

    PETA("P", pow(1000, 5)),

    EXA("E", pow(1000, 6)),

    ZETTA("Z", pow(1000, 7)),

    YOTTA("Y", pow(1000, 8)),

    KIBI("Ki", pow(1024, 1)),

    MEBI("Mi", pow(1024, 2)),

    GIBI("Gi", pow(1024, 3)),

    TEBI("Ti", pow(1024, 4)),

    PEBI("Pi", pow(1024, 5)),

    EXBI("Ei", pow(1024, 6)),

    ZEBI("Zi", pow(1024, 7)),

    YOBI("Yi", pow(1024, 8));

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
