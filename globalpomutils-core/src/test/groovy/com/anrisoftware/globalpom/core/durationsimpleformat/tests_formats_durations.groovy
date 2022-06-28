/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static com.anrisoftware.globalpom.core.durationsimpleformat.UnitMultiplier.*

import org.joda.time.Duration

[
    [format: "64ms", value: Duration.millis(64), multiplier: MILLISECONDS],
    [format: "64s", value: Duration.standardSeconds(64), multiplier: SECONDS],
    [format: "64m", value: Duration.standardMinutes(64), multiplier: MINUTES],
    [format: "64h", value: Duration.standardHours(64), multiplier: HOURS],
    [format: "65d", value: Duration.standardDays(65), multiplier: DAYS],
]
