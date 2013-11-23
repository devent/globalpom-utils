package com.anrisoftware.globalpom.constantsmap;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.constants.StandardConstantFactory;
import com.anrisoftware.globalpom.format.constants.ConstantFormat;
import com.anrisoftware.globalpom.format.constants.ConstantFormatFactory;
import com.anrisoftware.globalpom.format.measurement.ValueFormat;
import com.anrisoftware.globalpom.format.measurement.ValueFormatFactory;
import com.anrisoftware.globalpom.measurement.ExactStandardValueFactory;
import com.anrisoftware.globalpom.measurement.StandardValueFactory;
import com.google.inject.Provider;

/**
 * Provides the physical constants that calculates error propagation using
 * standard uncertainty.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Singleton
public class StandardConstantsProvider implements Provider<Constants> {

	@Inject
	private ConstantFormatFactory formatFactory;

	@Inject
	private StandardConstantFactory constantFactory;

	@Inject
	private ValueFormatFactory valueFormatFactory;

	@Inject
	private StandardValueFactory valueFactory;

	@Inject
	private ExactStandardValueFactory exactFactory;

	private Constants constants;

	@Inject
	void setConstantsFactory(ConstantsFactory constantsFactory) {
		ValueFormat valueFormat;
		ConstantFormat format;
		valueFormat = valueFormatFactory.create(valueFactory, exactFactory);
		format = formatFactory.create(constantFactory, valueFormat);
		this.constants = constantsFactory.create(format);
	}

	@Override
	public Constants get() {
		return constants;
	}

}
