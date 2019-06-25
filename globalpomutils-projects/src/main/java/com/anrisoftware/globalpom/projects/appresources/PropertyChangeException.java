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
package com.anrisoftware.globalpom.projects.appresources;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

/**
 * Property change exception on the application thread.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@SuppressWarnings("serial")
public class PropertyChangeException extends ContextedRuntimeException {

    private static final String VALUE_LABEL = "value";

    private static final String OLD_VALUE_LABEL = "old value";

    private static final String PROPERTY_LABEL = "property";

    public PropertyChangeException(Object message, Throwable e,
            Object property, Object oldValue, Object value) {
        super(message.toString(), e);
        addContextValue(PROPERTY_LABEL, property.toString());
        addContextValue(OLD_VALUE_LABEL, oldValue);
        addContextValue(VALUE_LABEL, value);
    }

}
