/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format.byteformat;

import java.text.NumberFormat;

/**
 * Factory to create a new computer byte format.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface ByteFormatFactory {

    /**
     * Create value format that uses the default number format to format the
     * computer byte.
     * 
     * @return the {@link ByteFormat}.
     */
    ByteFormat create();

    /**
     * Create value format with the specified number format to format the
     * computer byte.
     * 
     * @param format
     *            the {@link NumberFormat}.
     * 
     * @return the {@link ByteFormat}.
     */
    ByteFormat create(NumberFormat format);
}
