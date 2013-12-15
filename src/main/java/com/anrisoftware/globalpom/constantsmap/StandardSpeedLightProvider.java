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
