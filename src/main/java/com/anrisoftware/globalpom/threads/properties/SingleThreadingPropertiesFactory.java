package com.anrisoftware.globalpom.threads.properties;

import com.anrisoftware.propertiesutils.ContextProperties;

/**
 * Factory to create the single thread pool properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
interface SingleThreadingPropertiesFactory {

	SingleThreadingProperties create(ContextProperties properties, String name);
}
