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

import java.text.Format;
import java.text.ParseException;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

/**
 * Parses the column.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class TypedColumn implements Column {

    private final String name;

    private final Format format;

    /**
     * @see StringColumnFactory#create(String)
     */
    @Inject
    TypedColumn(@Assisted String name, @Assisted Format format) {
        this.name = name;
        this.format = format;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object parseValue(String string) throws ParseException {
        if (string.isEmpty()) {
            return null;
        } else {
            return format.parseObject(string);
        }
    }

}
