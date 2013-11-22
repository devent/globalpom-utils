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
package com.anrisoftware.globalpom.constantsmap

import org.junit.BeforeClass

import com.anrisoftware.globalpom.constants.ConstantsModule
import com.anrisoftware.globalpom.constants.StandardConstantFactory
import com.anrisoftware.globalpom.format.constants.ConstantFormatFactory
import com.anrisoftware.globalpom.format.constants.ConstantFormatModule
import com.anrisoftware.globalpom.format.measurement.ValueFormatFactory
import com.anrisoftware.globalpom.format.measurement.ValueFormatModule
import com.anrisoftware.globalpom.measurement.ExactStandardValueFactory
import com.anrisoftware.globalpom.measurement.MeasurementModule
import com.anrisoftware.globalpom.measurement.StandardValueFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Creates the constants factories.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class ConstantsTestBase {

	static Injector injector

	static ConstantsFactory constantsFactory

	static ConstantFormatFactory formatFactory

	static StandardConstantFactory constantFactory

	static ValueFormatFactory valueFormatFactory

	static StandardValueFactory value

	static ExactStandardValueFactory exact

	@BeforeClass
	static void createFactories() {
		TestUtils.toStringStyle
		injector = Guice.createInjector new ConstantsMapModule(), new ConstantsModule(),
				new ConstantFormatModule(), new ValueFormatModule(),
				new MeasurementModule()
		constantsFactory = injector.getInstance ConstantsFactory
		formatFactory = injector.getInstance ConstantFormatFactory
		constantFactory = injector.getInstance StandardConstantFactory
		valueFormatFactory = injector.getInstance ValueFormatFactory
		value = injector.getInstance StandardValueFactory
		exact = injector.getInstance ExactStandardValueFactory
	}
}
