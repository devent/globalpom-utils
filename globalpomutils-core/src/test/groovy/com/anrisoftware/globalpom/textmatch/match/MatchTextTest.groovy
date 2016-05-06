/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.textmatch.match

import static com.anrisoftware.globalpom.utils.TestUtils.*

import java.util.regex.Pattern

import org.apache.commons.io.FileUtils
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Test compare text files and resources.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
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

    @BeforeClass
    static void createFactories() {
        toStringStyle
        injector = Guice.createInjector(new MatchTextModule())
        factory = injector.getInstance MatchTextFactory
    }
}
