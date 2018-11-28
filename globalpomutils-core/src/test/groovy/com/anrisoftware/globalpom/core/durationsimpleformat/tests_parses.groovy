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
package com.anrisoftware.globalpom.core.durationsimpleformat

[
    [input: "64", value: 64],
    [input: "64ms", value: 64],
    [input: "64s", value: (long)64.0 * 1000],
    [input: "64m", value: (long)64.0 * 1000 * 60],
    [input: "64h", value: (long)64.0 * 1000 * 60 * 60],
    [input: "64d", value: (long)64.0 * 1000 * 60 * 60 * 24],
    [input: "64w", value: (long)64.0 * 1000 * 60 * 60 * 24 * 7],
    [input: "64M", value: (long)64.0 * 1000 * 60 * 60 * 24 * 30],
    [input: "64y", value: (long)64.0 * 1000 * 60 * 60 * 24 * 365],
]
