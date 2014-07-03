/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.measurement;

import static org.apache.commons.lang3.StringUtils.repeat;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.math3.util.FastMath;

/**
 * Formats a value to string.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class ValueToString implements Serializable {

    private static final String DECIMAL_PATTERN = "0.#";
    private static final String EXP = "E00";
    private static final char ZERO = '0';
    private static final String START_ZERO = "0.";
    private static final String CLOSE = ");";
    private static final String OPEN = "(";
    private static final String SEP = ";";

    /**
     * @see #format(StringBuffer, Value, NumberFormat, NumberFormat)
     */
    public StringBuffer format(StringBuffer buff, Value value) {
        NumberFormat format = createFormat(value);
        return format(buff, value, format);
    }

    /**
     * @see #format(StringBuffer, Value, NumberFormat, NumberFormat)
     */
    public StringBuffer format(StringBuffer buff, Value value,
            NumberFormat format) {
        return format(buff, value, format, format);
    }

    /**
     * Formats the specified point.
     * <p>
     * The format follows the pattern:
     * 
     * <pre>
     * &lt;value&gt;[(&lt;uncertainty&gt;);&lt;significant&gt;;&lt;decimal&gt;;]
     * </pre>
     * 
     * <p>
     * <h2>Examples</h2>
     * <p>
     * <ul>
     * <li>exact value: {@code 0.0123;}
     * <li>uncertain value: {@code 5.0(0.2);1;1;}
     * </ul>
     * 
     * @param buff
     *            the {@link StringBuffer} buffer to append the formatted value.
     * 
     * @param value
     *            the {@link Value}.
     * 
     * @param valueFormat
     *            the {@link NumberFormat} to format the value.
     * 
     * @param uncFormat
     *            the {@link NumberFormat} to format the uncertainly.
     */
    public StringBuffer format(StringBuffer buff, Value value,
            NumberFormat valueFormat, NumberFormat uncFormat) {
        if (value.isExact()) {
            formatExactValue(buff, value, valueFormat);
        } else {
            formatValue(buff, value, valueFormat, uncFormat);
        }
        return buff;
    }

    private void formatExactValue(StringBuffer buff, Value value,
            NumberFormat format) {
        buff.append(format.format(value.getValue()));
        buff.append(SEP);
    }

    private void formatValue(StringBuffer buff, Value value,
            NumberFormat valueFormat, NumberFormat uncFormat) {
        double v = value.getValue();
        double u = value.getUncertainty();
        int sig = value.getSignificant();
        int dec = value.getDecimal();
        buff.append(valueFormat.format(v));
        buff.append(OPEN).append(uncFormat.format(u)).append(CLOSE).append(sig)
                .append(SEP).append(dec).append(SEP);
    }

    /**
     * Create number format based on the significant figures and decimal numbers
     * of the specified value.
     * 
     * @param value
     *            the {@link Value}.
     * 
     * @return the {@link NumberFormat}.
     */
    public static NumberFormat createFormat(Value value) {
        if (value.isExact()) {
            return new DecimalFormat(DECIMAL_PATTERN);
        }
        int sig = value.getSignificant();
        int dec = value.getDecimal();
        sig = FastMath.min(sig, 128);
        String pattern = START_ZERO + repeat(ZERO, sig) + EXP;
        NumberFormat format = new DecimalFormat(pattern);
        format.setMaximumFractionDigits(dec);
        return format;
    }

}
