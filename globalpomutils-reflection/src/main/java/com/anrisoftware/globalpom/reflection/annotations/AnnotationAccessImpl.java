/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.reflection.annotations;

/*-
 * #%L
 * Global POM Utilities :: Reflection
 * %%
 * Copyright (C) 2014 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;

import jakarta.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.reflect.MethodUtils;

import com.google.inject.assistedinject.Assisted;

/**
 * Read access to the elements of an annotation via reflection.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationAccessImpl implements AnnotationAccess {

    private static final String ACCESSIBLE_OBJECT = "accessible object";

    private static final String ANNOTATION = "annotation";

    private final Class<? extends Annotation> annotationClass;

    private final AccessibleObject accessible;

    /**
     * @see AnnotationAccessFactory#create(Class, AccessibleObject)
     *
     * @since 1.5
     */
    @Inject
    AnnotationAccessImpl(@Assisted Class<? extends Annotation> annotationClass,
            @Assisted AccessibleObject accessible) {
        this.annotationClass = annotationClass;
        this.accessible = accessible;
    }

    @Override
    public <T> T getValue() {
        return getValue("value");
    }

    @Override
    public <T> T getValue(String name) {
        Annotation a = getAnnotation();
        try {
            return asType(name, a);
        } catch (NoSuchMethodException e) {
            throw new GetValueError(e, annotationClass, accessible, name);
        } catch (IllegalAccessException e) {
            throw new GetValueError(e, annotationClass, accessible, name);
        } catch (InvocationTargetException e) {
            throw new GetValueError(e.getTargetException(), annotationClass,
                    accessible, name);
        }
    }

    @Override
    public boolean haveValue(String name) {
        getAnnotation();
        return MethodUtils.getAccessibleMethod(annotationClass, name) != null;
    }

    @Override
    public Annotation getAnnotation() {
        Annotation a = accessible.getAnnotation(annotationClass);
        if (a == null) {
            throw new NoAnnotationFoundError(annotationClass, accessible);
        }
        return a;
    }

    @SuppressWarnings("unchecked")
    private <T> T asType(String name, Annotation a)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        return (T) MethodUtils.invokeMethod(a, name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(ANNOTATION, annotationClass)
                .append(ACCESSIBLE_OBJECT, accessible).toString();
    }
}
