/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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
