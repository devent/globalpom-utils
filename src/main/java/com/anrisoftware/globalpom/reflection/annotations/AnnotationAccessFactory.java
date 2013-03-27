package com.anrisoftware.globalpom.reflection.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Factory to create the access to the elements of an annotation.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationAccessFactory {

	/**
	 * Creates the annotation access.
	 * 
	 * @param annotationClass
	 *            the {@link Class} type of the annotation.
	 * 
	 * @param field
	 *            the {@link Field} where the annotation is added.
	 * 
	 * @return the {@link AnnotationAccess}.
	 */
	AnnotationAccess create(Class<? extends Annotation> annotationClass,
			Field field);
}
