/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.data.csvimport;

import java.io.IOException;

import org.apache.commons.lang3.exception.DefaultExceptionContext;

/**
 * Import exception.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class CsvImportException extends IOException {

    private final DefaultExceptionContext exceptionContext;

    /**
     * @see IOException#IOException(String, Throwable)
     *
     * @param message the {@link String} message.
     *
     * @param cause   the {@link Throwable} cause.
     */
    public CsvImportException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionContext = new DefaultExceptionContext();
    }

    /**
     * @see IOException#IOException(String)
     *
     * @param message the {@link String} message.
     */
    public CsvImportException(String message) {
        super(message);
        this.exceptionContext = new DefaultExceptionContext();
    }

    /**
     * @see IOException#IOException(String, Throwable)
     *
     * @param message the message.
     *
     * @param cause   the {@link Throwable} cause.
     */
    public CsvImportException(Object message, Throwable cause) {
        super(message.toString(), cause);
        this.exceptionContext = new DefaultExceptionContext();
    }

    /**
     * @see IOException#IOException(String)
     *
     * @param message the message.
     */
    public CsvImportException(Object message) {
        super(message.toString());
        this.exceptionContext = new DefaultExceptionContext();
    }

    /**
     *
     * @param label the {@link String} label.
     *
     * @param value the value.
     *
     * @return the {@link CsvImportException}
     */
    public CsvImportException addContextValue(String label, Object value) {
        exceptionContext.addContextValue(label, value);
        return this;
    }

    @Override
    public String getMessage() {
        return getFormattedExceptionMessage(super.getMessage());
    }

    /**
     *
     * @return the {@link String} raw message.
     */
    public String getRawMessage() {
        return super.getMessage();
    }

    public String getFormattedExceptionMessage(String baseMessage) {
        return exceptionContext.getFormattedExceptionMessage(baseMessage);
    }

}
