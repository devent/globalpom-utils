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
package com.anrisoftware.globalpom.core.textmatch.match

import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.util.regex.Pattern

import org.apache.commons.io.FileUtils
import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport
import org.junit.rules.TemporaryFolder

import com.anrisoftware.globalpom.utils.TestUtils

import groovy.util.logging.Slf4j

/**
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
@EnableRuleMigrationSupport
class MatchTextServiceTest {

    final OsgiContext context = new OsgiContext()

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    @Test
    void "compare text files matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/aaa/)
        def file = folder.newFile("file_a")
        FileUtils.write file, text
        MatchText worker = service.create file, pattern, charset
        worker.match()
        assert worker.matches == true
    }

    MatchTextService service

    @BeforeEach
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new MatchTextServiceImpl(), null)
    }
}
