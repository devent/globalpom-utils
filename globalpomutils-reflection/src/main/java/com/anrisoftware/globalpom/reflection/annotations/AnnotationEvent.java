/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
