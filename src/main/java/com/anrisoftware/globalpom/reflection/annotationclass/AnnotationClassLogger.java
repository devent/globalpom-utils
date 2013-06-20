package com.anrisoftware.globalpom.reflection.annotationclass;

import static org.apache.commons.lang3.Validate.notBlank;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link AnnotationClass}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@Singleton
class AnnotationClassLogger extends AbstractLogger {

	private static final String ONE_CLASS_TYPES = "More then one class types are specified for attribute '{}'.";
	private static final String NAME_NULL = "Attribute name can not be null or empty.";

	/**
	 * Create logger for {@link AnnotationClass}.
	 */
	public AnnotationClassLogger() {
		super(AnnotationClass.class);
	}

	void checkAttributeName(String name) {
		notBlank(name, NAME_NULL);
	}

	void checkClassAttribute(Class<?>[] type, String name) {
		if (type.length > 1) {
			log.warn(ONE_CLASS_TYPES, name);
		}
	}
}
