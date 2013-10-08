/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
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

	@BeforeClass
	static void createFactories() {
		TestUtils.toStringStyle
		injector = Guice.createInjector new MeasurementModule()
		exactAverageValueFactory = injector.getInstance ExactAverageValueFactory
		averageValueFactory = injector.getInstance AverageValueFactory
		π = injector.getInstance(AveragePiProvider).get()
	}
}
