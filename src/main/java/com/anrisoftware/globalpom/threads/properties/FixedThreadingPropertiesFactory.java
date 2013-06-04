package com.anrisoftware.globalpom.threads.properties;

import com.anrisoftware.propertiesutils.ContextProperties;

/**
 * Factory to create the fixed thread pool properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
interface FixedThreadingPropertiesFactory {

	FixedThreadingProperties create(ContextProperties properties, String name);
}
