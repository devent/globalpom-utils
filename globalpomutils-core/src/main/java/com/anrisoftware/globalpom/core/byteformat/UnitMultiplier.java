/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.byteformat;

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
     * @param string the {@link String}.
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
