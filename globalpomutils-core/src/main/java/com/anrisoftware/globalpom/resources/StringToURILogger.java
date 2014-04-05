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
package com.anrisoftware.globalpom.resources;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link StringToURI}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class StringToURILogger extends AbstractLogger {

    private static final String PATH = "path";
    private static final String ERROR_CONVERT_MESSAGE = "Error convert path '%s' to URL.";
    private static final String ERROR_CONVERT = "Error convert path";

    /**
     * Create logger for {@link StringToURI}.
     */
    public StringToURILogger() {
        super(StringToURI.class);
    }

    ConvertException errorConvert(Exception e, String path) {
        return logException(
                new ConvertException(ERROR_CONVERT, e).add(PATH, path),
                ERROR_CONVERT_MESSAGE, path);
    }
}
