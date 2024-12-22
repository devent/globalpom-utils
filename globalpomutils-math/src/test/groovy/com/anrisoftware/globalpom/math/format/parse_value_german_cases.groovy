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
/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.math.format

[
    [input: "299792458,000000000", expected: valueFactory.create(299792458000000000, 9, 18, -9)],
    [input: "-1230", expected: valueFactory.create(-123, 4, 3, 1)],
    [input: "1230", expected: valueFactory.create(123, 4, 3, 1)],
    [input: "1030", expected: valueFactory.create(103, 4, 3, 1)],
    [input: "123", expected: valueFactory.create(123, 3, 3, 0)],
    [input: "10,00", expected: valueFactory.create(1000, 2, 4, -2)],
    [input: "5,6445E2", expected: valueFactory.create(56445, 3, 5, -2)],
    [input: "1", expected: valueFactory.create(1, 1, 1, 0)],
    [input: "-1", expected: valueFactory.create(-1, 1, 1, 0)],
    [input: "0", expected: valueFactory.create(0, 0, 1, -1)],
    [input: "-0", expected: valueFactory.create(0, 0, 1, -1)],
    [input: "7E-1", expected: valueFactory.create(7, 0, 1, -1)],
    [input: "0,7", expected: valueFactory.create(7, 0, 1, -1)],
    [input: "0,07", expected: valueFactory.create(7, -1, 1, -2)],
    [input: "0,050000000", expected: valueFactory.create(50000000, -1, 8, -9)],
    [input: "0,500000000", expected: valueFactory.create(500000000, 0, 9, -9)],
    [input: "0,005050000", expected: valueFactory.create(5050000, -2, 7, -9)],
    [input: "0,123E2", expected: valueFactory.create(123, 2, 3, -1)],
    [input: "-0,123E2", expected: valueFactory.create(-123, 2, 3, -1)],
    [input: "12,3", expected: valueFactory.create(123, 2, 3, -1)],
    [input: "15,0", expected: valueFactory.create(150, 2, 3, -1)],
    [input: "11,02318310912886", expected: valueFactory.create(1102318310912886, 2, 16, -14)],
    [input: "1,23", expected: valueFactory.create(123, 1, 3, -2)],
    [input: "0,123", expected: valueFactory.create(123, 0, 3, -3)],
    [input: "0,0123", expected: valueFactory.create(123, -1, 3, -4)],
    [input: "0,00123", expected: valueFactory.create(123, -2, 3, -5)],
    [input: "0,123E-2", expected: valueFactory.create(123, -2, 3, -5)],
    [input: "12,123E-2", expected: valueFactory.create(12123, 0, 5, -5)],
    [input: "12,0123E-2", expected: valueFactory.create(120123, 0, 6, -6)],
    [input: "12,0123e-2", expected: valueFactory.create(120123, 0, 6, -6)],
]
