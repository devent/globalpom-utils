/*-
 * #%L
 * Global POM Utilities :: Exec
 * %%
 * Copyright (C) 2014 - 2018 Advanced Natural Research Institute
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
package com.anrisoftware.globalpom.exec.internal.command

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory
import com.anrisoftware.globalpom.exec.external.command.CommandLineService
import com.anrisoftware.globalpom.utils.TestUtils

import groovy.util.logging.Slf4j

/**
 * @see DefaultCommandLineServiceImpl
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
class DefaultCommandLineServiceImplTest extends AbstractCommandLineTest {

    final OsgiContext context = new OsgiContext()

    CommandLineFactory getCommandLineFactory() {
        service
    }

    CommandLineService service

    @BeforeEach
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new DefaultCommandLineServiceImpl(), null)
    }
}
