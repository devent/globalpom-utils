/*
 * Copyright 2015-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-projects.
 *
 * globalpomutils-projects is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-projects is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-projects. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.projects.appexceptions;

import org.apache.commons.lang3.exception.ContextedException;
import org.apache.commons.lang3.exception.ExceptionContext;

/**
 * Registration application exception.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@SuppressWarnings("serial")
public class AppException extends ContextedException {

    protected AppException() {
        super();
    }

    protected AppException(String message, Throwable cause,
            ExceptionContext context) {
        super(message, cause, context);
    }

    protected AppException(Object message, Throwable cause,
            ExceptionContext context) {
        super(message.toString(), cause, context);
    }

    protected AppException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AppException(Object message, Throwable cause) {
        super(message.toString(), cause);
    }

    protected AppException(String message) {
        super(message);
    }

    protected AppException(Object message) {
        super(message.toString());
    }

    protected AppException(Throwable cause) {
        super(cause);
    }

    protected AppException add(Object label, Object value) {
        return add(label.toString(), value);
    }

    protected AppException add(String label, Object value) {
        super.addContextValue(label, value);
        return this;
    }

}
