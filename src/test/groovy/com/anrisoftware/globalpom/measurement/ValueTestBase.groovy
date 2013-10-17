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
package com.anrisoftware.globalpom.measurement

import org.junit.BeforeClass

import com.anrisoftware.globalpom.constants.AveragePiProvider
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Creates the value factories.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ValueTestBase {

	static Injector injector

	static ExactAverageValueFactory exactAverageValueFactory

	static AverageValueFactory averageValueFactory

	static Value π

	static List averageValueData

	static List exactAverageValueData

	@BeforeClass
	static void createFactories() {
		TestUtils.toStringStyle
		injector = Guice.createInjector new MeasurementModule()
		exactAverageValueFactory = injector.getInstance ExactAverageValueFactory
		averageValueFactory = injector.getInstance AverageValueFactory
		π = injector.getInstance(AveragePiProvider).get()
		averageValueData = new AverageValueData().create(averageValueFactory, exactAverageValueFactory)
		exactAverageValueData = new ExactAverageValueData().create(averageValueFactory, exactAverageValueFactory)
	}
}
