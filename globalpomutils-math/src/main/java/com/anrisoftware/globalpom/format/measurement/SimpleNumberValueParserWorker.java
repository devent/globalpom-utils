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
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import com.anrisoftware.globalpom.measurement.Value;
import com.anrisoftware.globalpom.measurement.ValueFactory;

class SimpleNumberValueParserWorker extends AbstractValueParserWorker {

    public SimpleNumberValueParserWorker(DecimalFormatSymbols symbols,
            Locale locale, Integer significant, Integer decimal,
            ValueFactory valueFactory, String valueStr, String exponentStr,
            String uncString) {
        super(symbols, locale, significant, decimal, valueFactory, valueStr,
                exponentStr, uncString);
    }

    @Override
    public Value stringToValue(String string, Double unc, ParsePosition pos)
            throws ParseException {
        String decString = valueStr;
        if (exponentStr != null) {
            decString += exponentStr;
        }
        decString = setupDecimal(decString, decimal);
        splitValue(decString);
        parseExponent(exponentStr);
        int order = parseOrder();
        String mantissa = parseMantissa();
        int sig = mantissa.length();
        int dec = order - sig;
        pos.setIndex(string.length());
        pos.setErrorIndex(-1);
        return createValue(mantissa, unc, order, sig, dec);
    }

}
