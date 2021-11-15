/*
 * Copyright 2014-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static org.apache.commons.lang3.Validate.notNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.EventObject;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Base annotation discovered event.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
@SuppressWarnings("serial")
public class AnnotationEvent extends EventObject implements AnnotationBean {

	private final Annotation annotation;

	private final Object value;

	private final AccessibleObject member;

	/**
	 * Sets the annotation that was discovered.
	 * 
	 * @param source
	 *            the source object where the annotation was discovered.
	 * 
	 * @param a
	 *            the {@link Annotation}.
	 * 
	 * @param member
	 *            the {@link AccessibleObject} where the annotation was found.
	 * 
	 * @param value
	 *            the value of the field or method.
	 * 
	 * @throws NullPointerException
	 *             if the source, the annotation or the member is {@code null}.
	 * 
	 * @see EventObject#EventObject(Object)
	 */
	public AnnotationEvent(Object source, Annotation a,
			AccessibleObject member, Object value) {
		super(source);
		notNull(source, "The source cannot be null.");
		notNull(a, "The annotation cannot be null.");
		notNull(member, "The member cannot be null.");
		this.annotation = a;
		this.value = value;
		this.member = member;
	}

	/**
	 * Returns the annotation that was discovered.
	 * 
	 * @return the {@link Annotation}.
	 */
	@Override
	public Annotation getAnnotation() {
		return annotation;
	}

	/**
	 * Returns the bean member where the annotation was found.
	 * 
	 * @return the {@link AccessibleObject}.
	 */
	@Override
	public AccessibleObject getMember() {
		return member;
	}

	/**
	 * Returns the bean field where the annotation was found.
	 * 
	 * @return the {@link Field}.
	 * 
	 * @throws ClassCastException
	 *             if the bean member is not a field.
	 */
	@Override
	public Field getField() {
		return (Field) member;
	}

	/**
	 * Returns the bean method where the annotation was found.
	 * 
	 * @return the {@link Method}.
	 * 
	 * @throws ClassCastException
	 *             if the bean member is not a method.
	 */
	@Override
	public Method getMethod() {
		return (Method) member;
	}

	/**
	 * Returns the value that the annotation is found.
	 * 
	 * @return the {@link Object} value or {@code null}.
	 */
	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).appendSuper(super.toString())
				.append("annotation", annotation).append("member", member)
				.toString();
	}
}
