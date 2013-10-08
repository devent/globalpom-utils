/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.constants;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.measurement.AverageValueFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.google.inject.Provider;

/**
 * Provides the value of the electron mass {@code  me=9.10938291e-31 kg} that
 * calculates error propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@Singleton
public class AverageElectronMassProvider implements Provider<Value> {

	private static final double m = 9.10938291e-31;

	private static final int significant = 2;

	private static final double uncertainty = 0.00000040e-31;

	private static final int decimal = 8;

	@Inject
	private AverageValueFactory factory;

	@Override
	public Value get() {
		return factory.create(m, significant, uncertainty, decimal);
	}

}
