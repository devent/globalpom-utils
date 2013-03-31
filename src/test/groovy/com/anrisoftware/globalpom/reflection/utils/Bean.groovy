/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.reflection.utils

import java.beans.PropertyChangeEvent
import java.beans.PropertyVetoException


/**
 * Bean to test fields access.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class Bean {

	@BeanAnnotation(value = "Annotation Value", title = "Annotation Title")
	public String annotatedField = 'Text'

	String stringField = 'Text'

	int intField = 5

	String getterField = 'Getter Text'

	boolean getterOfGetterFieldCalled = false

	String setterField = 'Getter Text'

	boolean setterOfSetterFieldCalled = false

	String getterFieldThatThrowsException = 'Getter Text'

	String setterFieldThatThrowsException = 'Setter Text'

	boolean getterMethodOnlyCalled = false

	boolean setterMethodOnlyCalled = false

	boolean getterMethodOnlyVetoExceptionCalled = false

	boolean setterMethodOnlyVetoExceptionCalled = false

	String getGetterField() {
		getterOfGetterFieldCalled = true
		return getterField
	}

	void setSetterField(String value) {
		setterOfSetterFieldCalled = true
		setterField = value
	}

	String getGetterFieldThatThrowsException() {
		throw new Exception("Error in getter")
	}

	void setSetterFieldThatThrowsException(String value) {
		throw new Exception("Error in setter")
	}

	String getMethodOnly() {
		getterMethodOnlyCalled = true
		return "MethodOnly"
	}

	void setMethodOnly(String value) {
		setterMethodOnlyCalled = true
	}

	String getGetterMethodOnlyVetoException() {
		getterMethodOnlyVetoExceptionCalled = true
		return "veto"
	}

	void setGetterMethodOnlyVetoException(String value) throws PropertyVetoException {
		setterMethodOnlyVetoExceptionCalled = true
		def name = "methodOnlyVetoException"
		def event = new PropertyChangeEvent(this, name, null, null)
		throw new PropertyVetoException(name, event)
	}

	@BeanAnnotation(value = "Annotation Method", title = "Method Title")
	String getAnnotatedMethod() {
		return "AnnotatedMethod"
	}

	@BeanAnnotation(value = "Annotation Method Boolean", title = "Method Boolean Title")
	boolean isAnnotatedMethodBoolean() {
		return "AnnotatedMethodBoolean"
	}
}

