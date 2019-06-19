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
package com.anrisoftware.globalpom.core.system

import org.junit.jupiter.api.Test

import groovy.util.logging.Slf4j

/**
 * @see SystemSelector
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@Slf4j
class SystemSelectorTest {

    @Test
    void "get system"() {
        def name = System.getProperty("os.name")
        def system = SystemSelector.getSystem()
        log.info "System for {}: {}", name, system
    }
}
