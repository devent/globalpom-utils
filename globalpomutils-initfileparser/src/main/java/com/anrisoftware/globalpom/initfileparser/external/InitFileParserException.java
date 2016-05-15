/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-initfileparser.
 *
 * globalpomutils-initfileparser is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-initfileparser is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-initfileparser. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.initfileparser.external;

import java.io.IOException;

import org.apache.commons.lang3.exception.DefaultExceptionContext;

/**
 * INI file parser exception.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class InitFileParserException extends IOException {

    private final DefaultExceptionContext exceptionContext;

    protected InitFileParserException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionContext = new DefaultExceptionContext();
    }

    protected InitFileParserException(String message) {
        super(message);
        this.exceptionContext = new DefaultExceptionContext();
    }

    public InitFileParserException addContextValue(String label, Object value) {
        exceptionContext.addContextValue(label, value);
        return this;
    }

    @Override
    public String getMessage() {
        return getFormattedExceptionMessage(super.getMessage());
    }

    public String getRawMessage() {
        return super.getMessage();
    }

    public String getFormattedExceptionMessage(String baseMessage) {
        return exceptionContext.getFormattedExceptionMessage(baseMessage);
    }

}
