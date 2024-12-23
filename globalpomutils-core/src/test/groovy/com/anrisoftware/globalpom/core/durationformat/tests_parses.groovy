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
package com.anrisoftware.globalpom.core.durationformat

[
    [input: "P10Y20M30DT4H5M6.7S", duration: 370808946700l],
    [input: "P20M30DT4H5M6.7S", duration: 55207026700l],
    [input: "PT4H5M6.7S", duration: 14706700],
    [input: "PT4S", duration: 4l*1000l],
    [input: "PT4H", duration: 4l*60l*60l*1000l],
]
