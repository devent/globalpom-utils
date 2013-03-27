package com.anrisoftware.globalpom.reflection.utils

/**
 * Bean to test fields access.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class Bean {

	@BeanAnnotation(value = "Annotation Value", title = "Annotation Title")
	String annotatedField = 'Text'

	String stringField = 'Text'

	int intField = 5

	String getterField = 'Getter Text'

	boolean getterOfGetterFieldCalled = false

	String setterField = 'Getter Text'

	boolean setterOfSetterFieldCalled = false

	String getterFieldThatThrowsException = 'Getter Text'

	String setterFieldThatThrowsException = 'Setter Text'

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
}

