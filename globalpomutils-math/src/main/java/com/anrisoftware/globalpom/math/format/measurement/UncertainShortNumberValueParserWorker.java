/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.format.measurement;

/*-
 * #%L
 * Global POM Utilities :: Math
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

import static com.anrisoftware.globalpom.math.math.MathUtils.calculateValue;

import java.math.BigInteger;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import org.apache.commons.math3.util.FastMath;

import com.anrisoftware.globalpom.math.measurement.Value;
import com.anrisoftware.globalpom.math.measurement.ValueFactory;

class UncertainShortNumberValueParserWorker extends AbstractValueParserWorker {

    public UncertainShortNumberValueParserWorker(DecimalFormatSymbols symbols,
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
        if (uncString != null) {

        }
        int order = parseOrder();
        String mantissa = parseMantissa();
        int sig = mantissa.length();
        int dec = order - sig;
        return createValueSimpleUnc(mantissa, unc, order, sig, dec);
    }

    protected Value createValueSimpleUnc(String mantissa, Double unc,
            int order, int sig, int dec) {
        BigInteger man = new BigInteger(mantissa);
        man = negative ? man.negate() : man;
        if (unc == null) {
            unc = Double.NaN;
            if (uncString != null) {
                String ustr = uncString;
                unc = parseUncertainty(ustr, calculateValue(man, dec));
                unc *= FastMath.pow(10, dec);
            }
        }
        return valueFactory.create(man, order, sig, dec, unc);
    }

}
