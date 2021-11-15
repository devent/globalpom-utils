/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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

/**
 * Test the round to zero algorithm.
 *
 * @see MathUtils
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class FixTest {

    static double[] inputs = [
        -5.2,
        5.2,
        -1.9,
        -0.2,
        3.4,
        5.6,
        7.0
    ]

    static double[] outputs = [
        -5.0,
        5.0,
        -1.0,
        0.0,
        3.0,
        5.0,
        7.0
    ]

    @Test
    void "fix inputs to outputs"() {
        inputs.eachWithIndex { double value, int i ->
            double result = MathUtils.fix(value)
            assert outputs[i] == result
        }
    }
}
