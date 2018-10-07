/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
