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
import org.junit.Rule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport
import org.junit.rules.TemporaryFolder

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Test compare text files and resources.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@EnableRuleMigrationSupport
class MatchTextTest {

    @Test
    void "compare text files matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/aaa/)
        def file = folder.newFile("file_a")
        FileUtils.write file, text
        MatchText worker = factory.create file, pattern, charset
        worker.match()
        assert worker.matches == true
    }

    @Test
    void "compare text files not matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/bbb/)
        def file = folder.newFile("file_a")
        FileUtils.write file, text
        MatchText worker = factory.create file, pattern, charset
        worker.match()
        assert worker.matches == false
    }

    @Test
    void "serialize and compare text files matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/aaa/)
        def file = folder.newFile("file_a")
        FileUtils.write file, text
        MatchText worker = factory.create file, pattern, charset
        def workerB = reserialize worker
        workerB.match()
        assert workerB.matches == true
    }

    @Test
    void "compare text resource URI matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/aaa/)
        def file = folder.newFile("file_a")
        FileUtils.write file, text
        file = file.toURI()
        MatchText worker = factory.create file, pattern, charset
        worker.match()
        assert worker.matches == true
    }

    @Test
    void "compare text resource URL matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/aaa/)
        def file = folder.newFile("file_a")
        FileUtils.write file, text
        file = file.toURI().toURL()
        MatchText worker = factory.create file, pattern, charset
        worker.match()
        assert worker.matches == true
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    static Injector injector

    static MatchTextFactory factory

    @BeforeAll
    static void createFactories() {
        toStringStyle
        injector = Guice.createInjector(new MatchTextModule())
        factory = injector.getInstance MatchTextFactory
    }
}
