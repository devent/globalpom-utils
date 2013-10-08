/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement

import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.*
import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.Test

/**
 * @see RoundToSignificantFigures
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class RoundToSignificantFiguresTest {

	List inputs = [
		[value: 0.0d, sig: 2, result: 0.0d],
		[value: 2.0d, sig: 2, result: 2.0d],
		[value: 1239451d, sig: 3, result: 1240000d],
		[value: 12.1257d, sig: 3, result: 12.1d],
		[value: 0.0681d, sig: 3, result: 0.0681d],
		[value: 5d, sig: 3, result: 5d],
		[value: -2.0d, sig: 2, result: -2.0d],
		[value: -1239451d, sig: 3, result: -1240000d],
		[value: -12.1257d, sig: 3, result: -12.1d],
		[value: -0.0681d, sig: 3, result: -0.0681d],
		[value: -5d, sig: 3, result: -5d],
	]

	@Test
	void "round"() {
		epsilon = 10**-9
		inputs.each {
			double res = roundToSignificant it.value, it.sig
			assertDecimalEquals res, it.result
		}
	}
}
