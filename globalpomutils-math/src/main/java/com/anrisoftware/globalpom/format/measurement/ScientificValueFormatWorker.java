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

import static com.anrisoftware.globalpom.math.MathUtils.isFraction;
import static org.apache.commons.math3.util.FastMath.abs;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.apache.commons.math3.util.FastMath;

class ScientificValueFormatWorker extends AbstractValueFormatWorker {

    public ScientificValueFormatWorker(DecimalFormatSymbols symbols,
            Integer significant, Integer decimal) {
        super(symbols, significant, decimal);
    }

    @Override
    public String formatNumber(double value, int order, int sig, int dec) {
        StringBuilder pattern = new StringBuilder(DIGIT_STR);
        dec = abs(dec);
        double avalue = abs(value);
        double rvalue = ValueFormat.roundValue(value, sig, dec);
        if (dec != 0 && avalue < 1 || avalue > 9) {
            pattern.append(DECIMAL_CHAR);
            int s = avalue < 0 ? 0 : 1;
            for (int i = s; i < sig; i++) {
                pattern.append(DIGIT_CHAR);
            }
            pattern.append("E0");
        } else if (isFraction(avalue)) {
            pattern.append(DECIMAL_CHAR);
            for (int i = 1; i < sig; i++) {
                pattern.append(DIGIT_ZERO_CHAR);
            }
        }
        DecimalFormat format = new DecimalFormat(pattern.toString(), symbols);
        return format.format(rvalue);
    }

    @Override
    public String formatUncertainty(double unc, int order, int sig, int dec) {
        if (unc == 0) {
            return DIGIT_STR;
        }
        double aunc = abs(unc);
        int oorder = order - 1;
        double vunc = unc / FastMath.pow(10, oorder);
        StringBuilder pattern = new StringBuilder(DIGIT_STR);
        if (dec != 0) {
            pattern.append(DECIMAL_CHAR);
            int s = aunc < 0 ? 0 : 1;
            for (int i = s; i < sig; i++) {
                pattern.append(DIGIT_CHAR);
            }
        }
        DecimalFormat format = new DecimalFormat(pattern.toString(), symbols);
        String str = format.format(vunc);
        if (oorder != 0) {
            str = String.format("%sE%d", str, oorder);
        }
        return str;
    }

}
