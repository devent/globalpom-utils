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

import java.text.DecimalFormatSymbols;

import org.apache.commons.math3.util.FastMath;

import com.anrisoftware.globalpom.measurement.Value;

abstract class AbstractValueFormatWorker implements ValueFormatWorker {

    protected final DecimalFormatSymbols symbols;

    protected final Integer significant;

    protected final Integer decimal;

    protected AbstractValueFormatWorker(DecimalFormatSymbols symbols,
            Integer significant, Integer decimal) {
        this.symbols = symbols;
        this.significant = significant;
        this.decimal = decimal;
    }

    @Override
    public StringBuffer valueToString(Value value, StringBuffer buff) {
        double v = value.getValue();
        double unc = value.getRoundedUncertainty();
        int order = value.getOrder();
        int sig = significant == null ? value.getSignificant() : significant;
        int dec = value.getDecimal();
        if (decimal != null) {
            dec = FastMath.max(-decimal, dec);
        }
        buff.append(formatNumber(v, order, sig, dec));
        if (!value.isExact()) {
            buff.append('(');
            buff.append(formatUncertainty(unc, order, sig, dec));
            buff.append(')');
        }
        return buff;
    }
}
