package com.anrisoftware.globalpom.reflection.annotationclass;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Factory to create the annotation class builder.
 * 
 * @see AnnotationClass
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public interface AnnotationClassFactory {

	/**
	 * Creates the annotation class builder.
	 * 
	 * @param parent
	 *            the parent {@link Object} where the field can be found.
	 * 
	 * @param annotation
	 *            the {@link Class} type of the annotation.
	 * 
	 * @param object
	 *            the {@link Field} or the {@link Method} where the annotation
	 *            was added.
	 * 
	 * @return the {@link AnnotationClass}.
	 */
	AnnotationClass<?> create(Object parent,
			Class<? extends Annotation> annotation, AccessibleObject object);
}
