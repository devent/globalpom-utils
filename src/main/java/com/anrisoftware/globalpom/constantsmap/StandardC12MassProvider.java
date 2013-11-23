package com.anrisoftware.globalpom.constantsmap;

import java.text.ParseException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.measure.quantity.Mass;

import com.anrisoftware.globalpom.constants.Constant;
import com.google.inject.Provider;

/**
 * Provides the physical constants of the carbon atom mass {@code C12} that
 * calculates error propagation using standard uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Singleton
public class StandardC12MassProvider implements Provider<Constant<Mass>> {

	private static final String NAME = "carbon_mass";

	private Constant<Mass> constant;

	@Inject
	void setStandardConstantsProvider(StandardConstantsProvider provider)
			throws ParseException {
		this.constant = getConstant(provider.get());
	}

	@Override
	public Constant<Mass> get() {
		return constant;
	}

	@SuppressWarnings("unchecked")
	private Constant<Mass> getConstant(Constants constants)
			throws ParseException {
		return (Constant<Mass>) constants.getConstant(NAME);
	}

}
