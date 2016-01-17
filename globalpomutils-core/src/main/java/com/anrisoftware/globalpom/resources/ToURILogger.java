/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.resources.ToURILogger._.error_convert;
import static com.anrisoftware.globalpom.resources.ToURILogger._.error_convert_message;
import static com.anrisoftware.globalpom.resources.ToURILogger._.error_convert_scheme_message;
import static com.anrisoftware.globalpom.resources.ToURILogger._.path_name;
import static com.anrisoftware.globalpom.resources.ToURILogger._.scheme_name;
import static com.anrisoftware.globalpom.resources.ToURILogger._.url;

import java.net.URL;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link ToURL}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class ToURILogger extends AbstractLogger {

    enum _ {

        error_convert("Error convert path"),

        url("URL"),

        error_convert_message("Error convert path '{}'."),

        path_name("path"),

        scheme_name("scheme"), error_convert_scheme_message(
                "Error convert path '{}{}'.");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Create logger for {@link ToURL}.
     */
    public ToURILogger() {
        super(ToURL.class);
    }

    ConvertException errorConvert(Exception e, URL uri) {
        return logException(
                new ConvertException(error_convert, e).add(url, uri),
                error_convert_message, uri);
    }

    ConvertException errorConvert(Exception e, String path) {
        return logException(
                new ConvertException(error_convert, e).add(path_name, path),
                error_convert_message, path);
    }

    ConvertException errorConvert(Exception e, String path, String scheme) {
        return logException(
                new ConvertException(error_convert, e).add(path_name, path)
                        .add(scheme_name, scheme),
                error_convert_scheme_message, path, scheme);
    }
}
