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
package com.anrisoftware.globalpom.format.measurement;

import static org.apache.commons.math3.util.FastMath.abs;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

class NormalValueFormatWorker extends AbstractValueFormatWorker {

    public NormalValueFormatWorker(DecimalFormatSymbols symbols,
            Integer significant, Integer decimal) {
        super(symbols, significant, decimal);
    }

    @Override
    public String formatNumber(double value, int order, int sig, int dec) {
        StringBuilder pattern = new StringBuilder(DIGIT_STR);
        dec = abs(dec);
        double rvalue = ValueFormat.roundValue(value, sig, dec);
        if (dec > 0) {
            pattern.append('.');
            for (int i = order; i < 0; i++) {
                pattern.append(DIGIT_CHAR);
            }
            for (int i = 0; i < sig; i++) {
                pattern.append(DIGIT_ZERO_CHAR);
            }
        }
        DecimalFormat format = new DecimalFormat(pattern.toString(), symbols);
        return format.format(rvalue);
    }

    @Override
    public String formatUncertainty(double unc, int order, int sig, int dec) {
        StringBuilder pattern = new StringBuilder(DIGIT_STR);
        if (dec < 0) {
            pattern.append(DECIMAL_CHAR);
            for (int i = dec; i < 0; i++) {
                pattern.append(DIGIT_CHAR);
            }
        }
        DecimalFormat format = new DecimalFormat(pattern.toString(), symbols);
        return format.format(unc);
    }

}
