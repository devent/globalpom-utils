/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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

import com.anrisoftware.globalpom.measurement.Value;

interface ValueFormatWorker {

    static final char DECIMAL_CHAR = '.';
    static final String DIGIT_STR = "0";
    static final char DIGIT_ZERO_CHAR = '#';
    static final char DIGIT_CHAR = '0';

    StringBuffer valueToString(Value value, StringBuffer buff);

    String formatNumber(double value, int order, int sig, int dec);

    String formatUncertainty(double unc, int order, int sig, int dec);

}
