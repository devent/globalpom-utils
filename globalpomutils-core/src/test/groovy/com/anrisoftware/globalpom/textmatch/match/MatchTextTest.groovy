/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

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
        def file = new File(tmpdir, "file_a")
        FileUtils.write file, text
        DefaultMatchText worker = factory.create file, pattern, charset
        worker.match()
        assert worker.matches == true
    }

    @Test
    void "compare text files not matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/bbb/)
        def file = new File(tmpdir, "file_a")
        FileUtils.write file, text
        DefaultMatchText worker = factory.create file, pattern, charset
        worker.match()
        assert worker.matches == false
    }

    @Test
    void "serialize and compare text files matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/aaa/)
        def file = new File(tmpdir, "file_a")
        FileUtils.write file, text
        DefaultMatchText worker = factory.create file, pattern, charset
        def workerB = reserialize worker
        workerB.match()
        assert workerB.matches == true
    }

    @Test
    void "compare text resource URI matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/aaa/)
        def file = new File(tmpdir, "file_a")
        FileUtils.write file, text
        file = file.toURI()
        DefaultMatchText worker = factory.create file, pattern, charset
        worker.match()
        assert worker.matches == true
    }

    @Test
    void "compare text resource URL matching"() {
        def text = "aaa"
        def pattern = Pattern.compile(/aaa/)
        def file = new File(tmpdir, "file_a")
        FileUtils.write file, text
        file = file.toURI().toURL()
        DefaultMatchText worker = factory.create file, pattern, charset
        worker.match()
        assert worker.matches == true
    }

    File tmpdir

    @Before
    void createTmpDir() {
        tmpdir = File.createTempDir("MatchTextTest", null)
    }

    @After
    void deleteTmpDir() {
        tmpdir.deleteDir()
    }

    static Injector injector

    static DefaultMatchTextFactory factory

    @BeforeClass
    static void createFactories() {
        toStringStyle
        injector = createInjector()
        factory = injector.getInstance DefaultMatchTextFactory
    }

    static Injector createInjector() {
        Guice.createInjector(new MatchTextModule())
    }
}
