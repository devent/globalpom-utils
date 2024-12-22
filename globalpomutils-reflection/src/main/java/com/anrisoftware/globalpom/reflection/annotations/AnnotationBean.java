/*
 * Copyright 2014-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotation discovered.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationBean {

	/**
	 * Returns the annotation that was discovered.
	 * 
	 * @return the {@link Annotation}.
	 */
	Annotation getAnnotation();

	/**
	 * Returns the bean member where the annotation was found.
	 * 
	 * @return the {@link AccessibleObject}.
	 */
	AccessibleObject getMember();

	/**
	 * Returns the bean field where the annotation was found.
	 * 
	 * @return the {@link Field}.
	 * 
	 * @throws ClassCastException
	 *             if the bean member is not a field.
	 */
	Field getField();

	/**
	 * Returns the bean method where the annotation was found.
	 * 
	 * @return the {@link Method}.
	 * 
	 * @throws ClassCastException
	 *             if the bean member is not a method.
	 */
	Method getMethod();

	/**
	 * Returns the value that the annotation is found.
	 * 
	 * @return the {@link Object} value or {@code null}.
	 */
	Object getValue();

}
