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
    [input: "1230.0(5)", expected: valueFactory.create(12300, 4, 5, -1, 0.5d)],
    [input: "1030.0(5)", expected: valueFactory.create(10300, 4, 5, -1, 0.5d)],
    [input: "123.0(5)", expected: valueFactory.create(1230, 3, 4, -1, 0.5d)],
    [input: "1.0(5)", expected: valueFactory.create(10, 1, 2, -1, 0.5d)],
    [input: "0.123(5)E5", expected: valueFactory.create(123, 5, 3, 2, 0.005e5d)],
    // 5
    [input: "1.23(5)", expected: valueFactory.create(123, 1, 3, -2, 0.05d)],
    [input: "0.123(5)", expected: valueFactory.create(123, 0, 3, -3, 0.005d)],
    [input: "0.0123(5)", expected: valueFactory.create(123, -1, 3, -4, 0.0005d)],
    [input: "0.00123(5)", expected: valueFactory.create(123, -2, 3, -5, 0.00005d)],
    [input: "0.123(5)E-2", expected: valueFactory.create(123, -2, 3, -5, 0.005e-2d)],
    // 10
    [input: "12.123(5)E-2", expected: valueFactory.create(12123, 0, 5, -5, 0.005e-2d)],
    [input: "12.0123(5)E-2", expected: valueFactory.create(120123, 0, 6, -6, 0.0005e-2d)],
    [input: "6.62606957(29)E-34", expected: valueFactory.create(662606957, -33, 9, -42, 2.9E-41)],
    [input: "2981.0(1.1)", expected: valueFactory.create(29810, 4, 5, -1, 1.1d)],
    [input: "547.853(24)", expected: valueFactory.create(547853, 3, 6, -3, 0.024d)],
    [input: "547.853(24)e3", expected: valueFactory.create(547853, 6, 6, 0, 0.024e3d)],
]
