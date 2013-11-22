package com.anrisoftware.globalpom.constantsmap;

import java.net.URL;

import com.anrisoftware.propertiesutils.AbstractContextPropertiesProvider;

/**
 * Provides physical constants resource {@code constants_values.properties}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
class ConstantsResourceProvider extends
		AbstractContextPropertiesProvider {

	private static final URL RES = ConstantsResourceProvider.class
			.getResource("/constants_values.properties");

	ConstantsResourceProvider() {
		super(ConstantsResourceProvider.class, RES);
	}

}
