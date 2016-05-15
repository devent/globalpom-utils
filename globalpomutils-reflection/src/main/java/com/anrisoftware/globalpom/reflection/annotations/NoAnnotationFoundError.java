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
package com.anrisoftware.globalpom.reflection.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

@SuppressWarnings("serial")
public class NoAnnotationFoundError extends ReflectionError {

    public NoAnnotationFoundError(Class<? extends Annotation> annotationClass,
            AccessibleObject accessible) {
        super("No annotation found");
        addContextValue("annotation-class", annotationClass);
        addContextValue("accessible", annotationClass);
    }

}
