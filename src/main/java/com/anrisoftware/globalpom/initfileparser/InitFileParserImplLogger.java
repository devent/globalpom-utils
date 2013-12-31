/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.initfileparser;

import static com.anrisoftware.globalpom.initfileparser.InitFileParserImplLogger._.line_iterator_error;
import static com.anrisoftware.globalpom.initfileparser.InitFileParserImplLogger._.line_iterator_error_message;
import static com.anrisoftware.globalpom.initfileparser.InitFileParserImplLogger._.multiline_property_error;
import static com.anrisoftware.globalpom.initfileparser.InitFileParserImplLogger._.multiline_property_error_message;
import static com.anrisoftware.globalpom.initfileparser.InitFileParserImplLogger._.open_stream_error;
import static com.anrisoftware.globalpom.initfileparser.InitFileParserImplLogger._.open_stream_error_message;
import static com.anrisoftware.globalpom.initfileparser.InitFileParserImplLogger._.resource;

import java.io.IOException;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link InitFileParserImpl}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class InitFileParserImplLogger extends AbstractLogger {

    enum _ {

        open_stream_error("Open stream error"),

        open_stream_error_message("Error open resource '{}'."),

        resource("resource"),

        line_iterator_error("Error read resource"),

        line_iterator_error_message("Error read resource '{}'."),

        multiline_property_error("Multi-line properties are not allowed"),

        multiline_property_error_message(
                "Multi-line properties are not allowed for resource '{}'.");

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
     * Sets the context of the logger to {@link InitFileParserImpl}.
     */
    public InitFileParserImplLogger() {
        super(InitFileParserImpl.class);
    }

    InitFileParserException openStreamError(InitFileParserImpl parser,
            Exception e) {
        return logException(
                new InitFileParserException(open_stream_error, e).add(resource,
                        parser.getResource()), open_stream_error_message,
                parser.getResource());
    }

    InitFileParserException lineIteratorError(InitFileParserImpl parser,
            IOException e) {
        return logException(
                new InitFileParserException(line_iterator_error, e).add(
                        resource, parser.getResource()),
                line_iterator_error_message, parser.getResource());
    }

    InitFileParserException errorMultiLineProperty(InitFileParserImpl parser) {
        return logException(new InitFileParserException(
                multiline_property_error).add(resource, parser.getResource()),
                multiline_property_error_message, parser.getResource());
    }
}
