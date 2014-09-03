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
package com.anrisoftware.globalpom.format.measurement;

import java.text.NumberFormat;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.anrisoftware.globalpom.measurement.ExactValueFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.anrisoftware.globalpom.measurement.ValueFactory;

/**
 * Formats the value as text.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
public class ValueRenderer {

    @Inject
    private ValueFormatFactory valueFormatFactory;

    private ValueFactory valueFactory;

    private ExactValueFactory exactValueFactory;

    private NumberFormat smallDecimalFormat;

    private NumberFormat normalDecimalFormat;

    private NumberFormat bigDecimalFormat;

    public void setValueFactory(ValueFactory factory) {
        this.valueFactory = factory;
    }

    public void setExactValueFactory(ExactValueFactory factory) {
        this.exactValueFactory = factory;
    }

    public void setSmallDecimalFormat(NumberFormat format) {
        this.smallDecimalFormat = format;
    }

    public void setNormalDecimalFormat(NumberFormat format) {
        this.normalDecimalFormat = format;
    }

    public void setBigDecimalFormat(NumberFormat format) {
        this.bigDecimalFormat = format;
    }

    public String formatValue(Value value) {
        NumberFormat format = numberFormat(value.getValue());
        NumberFormat uncformat = numberFormat(value.getUncertainty());
        String str = valueFormatFactory.create(valueFactory, exactValueFactory,
                format, uncformat).format(value);
        String[] split = StringUtils.split(str, ";");
        return split[0];
    }

    private NumberFormat numberFormat(double d) {
        if (d < 10e-4) {
            return smallDecimalFormat;
        }
        if (d > 10e4) {
            return bigDecimalFormat;
        }
        return normalDecimalFormat;
    }
}
