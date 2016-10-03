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
package com.anrisoftware.globalpom.format

[
    [input: "1230(0.5)", expected: valueFactory.create(1230000, 4, 7, -3, 0.5d)],
    [input: "1030(0.5)", expected: valueFactory.create(1030000, 4, 7, -3, 0.5d)],
    [input: "123(0.5)", expected: valueFactory.create(123000, 3, 6, -3, 0.5d)],
    [input: "1(0.5)", expected: valueFactory.create(1000, 1, 4, -3, 0.5d)],
    [input: "0.123E2(0.5)", expected: valueFactory.create(12300, 2, 5, -3, 0.5d)],
    [input: "1.23(0.5)", expected: valueFactory.create(1230, 1, 4, -3, 0.5d)],
    [input: "0.123(0.5)", expected: valueFactory.create(123, 0, 3, -3, 0.5d)],
    [input: "0.0123(0.5)", expected: valueFactory.create(12, -1, 2, -3, 0.5d)],
    [input: "0.00123(0.5)", expected: valueFactory.create(1, -2, 1, -3, 0.5d)],
    [input: "0.123E-2(0.5)", expected: valueFactory.create(1, -2, 1, -3, 0.5d)],
    [input: "12.123E-2(0.5)", expected: valueFactory.create(121, 0, 3, -3, 0.5d)],
    [input: "12.0123E-2(0.5)", expected: valueFactory.create(120, 0, 3, -3, 0.5d)],
    [input: "6.62606957E-34(0.00000029E-34)", expected: valueFactory.create(0, 0, 1, -1, 2.9E-41)],
]
