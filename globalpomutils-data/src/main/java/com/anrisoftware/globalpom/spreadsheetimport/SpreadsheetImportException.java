package com.anrisoftware.globalpom.spreadsheetimport;

import java.io.IOException;

import org.apache.commons.lang3.exception.DefaultExceptionContext;

/**
 * Thrown if there was an error importing the spreadsheet file.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.14
 */
@SuppressWarnings("serial")
public class SpreadsheetImportException extends IOException {

    private final DefaultExceptionContext exceptionContext;

    public SpreadsheetImportException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionContext = new DefaultExceptionContext();
    }

    public SpreadsheetImportException(String message) {
        super(message);
        this.exceptionContext = new DefaultExceptionContext();
    }

    @Override
    public String getMessage() {
        return getFormattedExceptionMessage(super.getMessage());
    }

    public SpreadsheetImportException addContextValue(String label, Object value) {
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
