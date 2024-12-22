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
 * Test the round to zero algorithm.
 *
 * @see MathUtils#decimalPlaces(String, char)
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
@Slf4j
class DecimalPlacesTest {

	@Test
	void "decimal places"() {
		def testCases = [
			[value: "0", result: 0 ],
			[value: "-5.2", result: 1 ],
			[value: "5.2", result: 1 ],
			[value: "-1.47956", result: 5 ],
			[value: "1.47672", result: 5 ],
			[value: "0.1E5", result: 4 ],
			[value: "0.1E-5", result: 6 ],
			[value: "1E5", result: 5 ],
			[value: "1E-5", result: 5 ],
		]
		char sep = '.'
		def exsep = 'E'
		testCases.eachWithIndex { testCase, int k ->
			log.info "{}. case: {}", k, testCase
			int result = MathUtils.decimalPlaces(testCase.value, sep, exsep)
			assert result == testCase.result
		}
	}
}
