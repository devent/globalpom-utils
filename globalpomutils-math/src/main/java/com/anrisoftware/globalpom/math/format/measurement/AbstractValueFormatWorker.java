/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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

import org.apache.commons.math3.util.FastMath;

import com.anrisoftware.globalpom.math.measurement.Value;

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
