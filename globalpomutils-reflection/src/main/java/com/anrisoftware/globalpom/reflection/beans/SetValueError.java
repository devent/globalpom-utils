/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-reflection.
 *
 * globalpomutils-reflection is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-reflection is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-reflection. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.reflection.beans;

import java.lang.reflect.Method;

import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

@SuppressWarnings("serial")
public class SetValueError extends ReflectionError {

    public SetValueError(Throwable cause, Object bean, String fieldName,
            Method setter) {
        super("Set value with setter error", cause);
        addContextValue("bean", bean);
        addContextValue("field-name", fieldName);
        addContextValue("setter", setter);
    }

    public SetValueError(Exception cause, Object bean, String fieldName) {
        super("Set value via field error", cause);
        addContextValue("bean", bean);
        addContextValue("field-name", fieldName);
    }

}