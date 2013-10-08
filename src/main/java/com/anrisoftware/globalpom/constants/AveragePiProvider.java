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

import static org.apache.commons.math3.util.FastMath.PI;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.anrisoftware.globalpom.measurement.ExactAverageValueFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.google.inject.Provider;

/**
 * Provides the value of Pi {@code π≈3.141592653589793} that calculates error
 * propagation using simpler average errors.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@Singleton
public class AveragePiProvider implements Provider<Value> {

	@Inject
	private ExactAverageValueFactory factory;

	@Override
	public Value get() {
		return factory.create(PI);
	}

}
