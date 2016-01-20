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
