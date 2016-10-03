/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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

