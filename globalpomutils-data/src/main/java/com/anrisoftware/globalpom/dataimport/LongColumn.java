/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-data.
 *
 * globalpomutils-data is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-data is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-data. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.dataimport;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.text.ParseException;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

/**
 * {@link Long} value column.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.5
 */
public class LongColumn implements Column {

    private final String name;

    /**
     * @see LongColumnFactory#create(String)
     */
    @Inject
    LongColumn(@Assisted String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @see Long#parseLong(String)
     */
    @Override
    public Object parseValue(String string) throws ParseException {
        if (isBlank(string)) {
            return null;
        } else {
            return Long.parseLong(string);
        }
    }

}