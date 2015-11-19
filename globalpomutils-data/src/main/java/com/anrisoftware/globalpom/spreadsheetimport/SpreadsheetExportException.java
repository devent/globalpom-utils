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
package com.anrisoftware.globalpom.spreadsheetimport;

import java.io.IOException;

import org.apache.commons.lang3.exception.DefaultExceptionContext;

/**
 * Thrown if there was an error exporting data to the spreadsheet file.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.14
 */
@SuppressWarnings("serial")
public class SpreadsheetExportException extends IOException {

    private final DefaultExceptionContext exceptionContext;

    public SpreadsheetExportException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionContext = new DefaultExceptionContext();
    }

    public SpreadsheetExportException(String message) {
        super(message);
        this.exceptionContext = new DefaultExceptionContext();
    }

    @Override
    public String getMessage() {
        return getFormattedExceptionMessage(super.getMessage());
    }

    public SpreadsheetExportException addContextValue(String label, Object value) {
        exceptionContext.addContextValue(label, value);
        return this;
    }

    public String getRawMessage() {
        return super.getMessage();
    }

    public String getFormattedExceptionMessage(String baseMessage) {
        return exceptionContext.getFormattedExceptionMessage(baseMessage);
    }

}
