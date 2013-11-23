package com.anrisoftware.globalpom.constantsmap;

import java.text.ParseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.measure.quantity.Velocity;

import com.anrisoftware.globalpom.constants.Constant;
import com.google.inject.Provider;

/**
 * Provides the physical constants of the speed of light {@code c} that
 * calculates error propagation using standard uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Singleton
public class StandardSpeedLightProvider implements Provider<Constant<Velocity>> {

	private static final String NAME = "speed_light";

	private Constant<Velocity> constant;

	@Inject
	void setStandardConstantsProvider(StandardConstantsProvider provider)
			throws ParseException {
		this.constant = getConstant(provider.get());
	}

	@Override
	public Constant<Velocity> get() {
		return constant;
	}

	@SuppressWarnings("unchecked")
	private Constant<Velocity> getConstant(Constants constants)
			throws ParseException {
		return (Constant<Velocity>) constants.getConstant(NAME);
	}

}
