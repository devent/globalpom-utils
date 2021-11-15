/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

import com.anrisoftware.globalpom.math.measurement.Value;
import com.anrisoftware.globalpom.math.measurement.ValueFactory;

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
