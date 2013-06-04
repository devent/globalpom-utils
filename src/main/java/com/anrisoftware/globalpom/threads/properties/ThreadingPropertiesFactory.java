package com.anrisoftware.globalpom.threads.properties;

import com.anrisoftware.propertiesutils.ContextProperties;

interface ThreadingPropertiesFactory {

	ThreadingProperties create(ContextProperties properties, String name);
}
