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

import java.lang.annotation.Annotation;

import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Read access to the elements of an annotation.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationAccess {

    /**
     * Returns the value of the annotation element "value".
     *
     * <pre>
     * &#064;SomeAnnotation(&quot;some-value&quot;)
     * FomeField
     *
     * String value = access.getValue();
     * assert value == &quot;some-value&quot;;
     * </pre>
     *
     * @param <T> the value type.
     *
     * @return the value of the annotation field.
     *
     * @throws ReflectionError if the element was not found in the annotation, the
     *                         element can not be accessed or the element throws an
     *                         exception.
     */
    <T> T getValue();

    /**
     * Returns the value of an annotation element.
     *
     * <pre>
     * &#064;SomeAnnotation(element = &quot;some-value&quot;)
     * FomeField
     *
     * String value = access.getValue("element");
     * assert value == &quot;some-value&quot;;
     * </pre>
     *
     * @param name the name of the annotation element.
     *
     * @param <T> the value type.
     *
     * @return the value of the annotation field.
     *
     * @throws ReflectionError if the element was not found in the annotation, the
     *                         element can not be accessed or the element throws an
     *                         exception.
     */
    <T> T getValue(String name);

    /**
     * Tests if the annotation have the element with the specified name.
     *
     * @param name the name of the annotation element.
     *
     * @return {@code true} if the annotation have the element with the specified
     *         name.
     */
    boolean haveValue(String name);

    /**
     * Returns the annotation.
     *
     * @return the {@link Annotation}.
     *
     * @since 1.5
     */
    Annotation getAnnotation();

}
