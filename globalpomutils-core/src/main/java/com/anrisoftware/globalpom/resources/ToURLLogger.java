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

import java.io.File;
import java.net.URI;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link ToURL}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class ToURLLogger extends AbstractLogger {

    private static final String SCHEME = "scheme";
    private static final String FILE = "file";
    private static final String URI = "URI";
    private static final String PATH = "path";
    private static final String ERROR_CONVERT_MESSAGE = "Error convert path '{}' to URL.";
    private static final String ERROR_CONVERT = "Error convert path";
    private static final String ERROR_CONVERT_SCHEME_MESSAGE = "Error convert path '{}{}' to URL.";

    /**
     * Create logger for {@link ToURL}.
     */
    public ToURLLogger() {
        super(ToURL.class);
    }

    ConvertException errorConvert(Exception e, String path) {
        return logException(
                new ConvertException(ERROR_CONVERT, e).add(PATH, path),
                ERROR_CONVERT_MESSAGE, path);
    }

    ConvertException errorConvert(Exception e, String path, String scheme) {
        return logException(
                new ConvertException(ERROR_CONVERT, e).add(PATH, path).add(
                        SCHEME, scheme), ERROR_CONVERT_SCHEME_MESSAGE, scheme,
                path);
    }

    ConvertException errorConvert(Exception e, URI uri) {
        return logException(
                new ConvertException(ERROR_CONVERT, e).add(URI, uri),
                ERROR_CONVERT_MESSAGE, uri);
    }

    ConvertException errorConvert(Exception e, File file) {
        return logException(
                new ConvertException(ERROR_CONVERT, e).add(FILE, file),
                ERROR_CONVERT_MESSAGE, file);
    }

}
