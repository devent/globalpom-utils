/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prospect-core. All rights reserved.
 */
package com.anrisoftware.globalpom.measurement

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.Test

import com.anrisoftware.globalpom.measurement.Value;

/**
 * @see ExactAverageValue
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ExactAvergaeValueTest extends ValueTestBase {

	@Test
	void "add exact exact"() {
		Value valuea = exactAverageValueFactory.create 5
		Value valueb = exactAverageValueFactory.create 6
		Value valuec = valuea.add valueb
		Value z = valuec.roundedValue
		assert valuec.value == 11
		assert valuec.exact
		assert z.value == 11
		assert z.exact
	}

	@Test
	void "sub exact exact"() {
		Value valuea = exactAverageValueFactory.create 5
		Value valueb = exactAverageValueFactory.create 6
		Value valuec = valuea.sub valueb
		assert valuec.value == -1
		assert valuec.exact
	}

	@Test
	void "mul exact exact"() {
		Value valuea = exactAverageValueFactory.create 5
		Value valueb = exactAverageValueFactory.create 6
		Value valuec = valuea.mul valueb
		assertDecimalEquals valuec.value, 5d*6d
		assert valuec.exact
	}

	@Test
	void "div exact exact"() {
		Value valuea = exactAverageValueFactory.create 5
		Value valueb = exactAverageValueFactory.create 6
		Value valuec = valuea.div valueb
		assertDecimalEquals valuec.value, 5d/6d
		assert valuec.exact
	}

	@Test
	void "exact string"() {
		Value value = exactAverageValueFactory.create 5
		assert value.toString() == "ExactAverageValue[5.000000]"
	}
}
