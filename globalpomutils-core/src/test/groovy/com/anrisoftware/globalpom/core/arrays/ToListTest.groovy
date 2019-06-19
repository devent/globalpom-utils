/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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
package com.anrisoftware.globalpom.core.arrays

import org.junit.jupiter.api.Test

/**
 * @see ToList
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ToListTest {

    @Test
    void "to list"() {
        inputs.each {
            assert ToList.toList(it.obj) == it.list
        }
    }

    static inputs = [
        [obj: ["A", "B"], list: ["A", "B"]],
        [obj: [1.0d, 2.0d] as double[], list: [1.0d, 2.0d]],
        [obj: [1l, 2l] as long[], list: [1l, 2l]],
        [obj: ["A", "B"] as String[], list: ["A", "B"]],
        [obj: "A", list: ["A"]],
    ]
}
