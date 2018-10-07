/*-
 * #%L
 * Global POM Utilities :: Math
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
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
    [input: "1230(1%)", expected: valueFactory.create(123, 4, 3, 1, 0.5d)],
    [input: "1030(1%)", expected: valueFactory.create(103, 4, 3, 1, 0.5d)],
    [input: "123(1%)", expected: valueFactory.create(123, 3, 3, 0, 0.5d)],
    [input: "1(1%)", expected: valueFactory.create(1, 1, 1, 0, 0.5d)],
    [input: "0.123E2(1%)", expected: valueFactory.create(123, 2, 3, -1, 0.5d)],
    [input: "1.23(1%)", expected: valueFactory.create(123, 1, 3, -2, 0.5d)],
    [input: "0.123(1%)", expected: valueFactory.create(123, 0, 3, -3, 0.5d)],
    [input: "0.0123(1%)", expected: valueFactory.create(123, -1, 3, -4, 0.5d)],
    [input: "0.00123(1%)", expected: valueFactory.create(123, -2, 3, -5, 0.5d)],
    [input: "0.123E-2(1%)", expected: valueFactory.create(123, -2, 3, -5, 0.5d)],
    [input: "12.123E-2(1%)", expected: valueFactory.create(12123, 0, 5, -5, 0.5d)],
    [input: "12.0123E-2(1%)", expected: valueFactory.create(120123, 0, 6, -6, 0.5d)],
]
