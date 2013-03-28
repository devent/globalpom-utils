package com.anrisoftware.globalpom.reflection.annotations;

import java.lang.reflect.Method;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Logging messages for {@link AnnotationDiscoveryImpl}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationDiscoveryImplLogger extends AbstractLogger {

	private static final String METHOD = "method";

	private static final String BEAN = "bean";

	private static final String ANNOTATION_DISCOVERY = "annotation discovery";

	private static final String METHOD_NOT_GETTER_MESSAGE = "Method is not getter %s#%s.";

	private static final String METHOD_NOT_GETTER = "Method is not getter";

	/**
	 * Create logger for {@link AnnotationDiscoveryImpl}.
	 */
	public AnnotationDiscoveryImplLogger() {
		super(AnnotationDiscoveryImpl.class);
	}

	ReflectionError methodNotGetter(AnnotationDiscoveryImpl a, Object bean,
			Method method) {
		return logException(
				new ReflectionError(METHOD_NOT_GETTER)
						.addContextValue(ANNOTATION_DISCOVERY, a)
						.addContextValue(BEAN, bean)
						.addContextValue(METHOD, method),
				METHOD_NOT_GETTER_MESSAGE, bean, method);
	}
}
