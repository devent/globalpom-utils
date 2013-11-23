package com.anrisoftware.globalpom.constantsmap;

import java.text.ParseException;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.constants.Constant;
import com.google.inject.Provider;

/**
 * Provides the physical constants of the electron charge {@code e} that
 * calculates error propagation using standard uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Singleton
public class StandardElectronChargeProvider implements
		Provider<Constant<?>> {

	private static final String NAME = "electron_charge";

	private Constant<?> constant;

	@Inject
	void setStandardConstantsProvider(StandardConstantsProvider provider)
			throws ParseException {
		this.constant = getConstant(provider.get());
	}

	@Override
	public Constant<?> get() {
		return constant;
	}

	private Constant<?> getConstant(Constants constants) throws ParseException {
		return constants.getConstant(NAME);
	}

}
