/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.math.math

import org.junit.jupiter.api.Test

import groovy.util.logging.Slf4j

/**
 * @see MathUtils#sigPlaces(String, char, String)
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
@Slf4j
class SigPlacesTest {

	static data = [
		[value: "4", sig: 1],
		[value: "1.3", sig: 2],
		[value: "4325.334", sig: 7],
		[value: "109", sig: 3],
		[value: "3.005", sig: 4],
		[value: "40.001", sig: 5],
		[value: "0.10", sig: 2],
		[value: "0.0010", sig: 2],
		[value: "3.20", sig: 3],
		[value: "320", sig: 2],
		[value: "14.3000", sig: 6],
		[value: "400.00", sig: 5],
		[value: "2.00E7", sig: 3],
		[value: "1.500E-2", sig: 4],
	]

	@Test
	void "significant places"() {
		char sep = '.'
		def exsep = 'E'
		data.each {
			log.info "{}", it.value
			int result = MathUtils.sigPlaces(it.value, sep, exsep)
			assert result == it.sig
		}
	}
}
