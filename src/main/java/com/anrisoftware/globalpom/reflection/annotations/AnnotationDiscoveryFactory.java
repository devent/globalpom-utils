package com.anrisoftware.globalpom.reflection.annotations;

/**
 * Factory to set the filter and the object for which annotations should be
 * found.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationDiscoveryFactory {

	/**
	 * Creates the new annotation search.
	 * 
	 * @param bean
	 *            the {@link Object}.
	 * 
	 * @param filter
	 *            the {@link AnnotationFilter} that will be used to filter the
	 *            annotation types.
	 * 
	 * @return the new {@link AnnotationDiscovery}.
	 */
	AnnotationDiscovery create(Object bean, AnnotationFilter filter);
}
