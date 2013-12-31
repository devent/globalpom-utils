/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.initfileparser

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see InitFileParser
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ParserTest {

    @Test
    void "parse INI-file"() {
        def attributes = attributesFactory.create()
        def parser = parserFactory.create(inifile, attributes)()
        List sections = parser.inject([]) { acc, val -> acc << val }
        assert sections.size() == 4
        assert sections[0].name == "ssh"
        assert sections[0].properties.size() == 5
    }

    @Test
    void "parse INI-file, no section"() {
        def attributes = attributesFactory.create()
        def parser = parserFactory.create(inifileNoSection, attributes)()
        List sections = parser.inject([]) { acc, val -> acc << val }
        assert sections.size() == 4
        assert sections[0].name == "Default"
        assert sections[0].properties.size() == 5
    }

    static URL inifile = ParserTest.class.getResource("inifile.txt")

    static URL inifileNoSection = ParserTest.class.getResource("inifile_no_section.txt")

    static Injector injector

    static InitFileParserFactory parserFactory

    static DefaultInitFileAttributesFactory attributesFactory

    @BeforeClass
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new InitFileParserModule())
        parserFactory = injector.getInstance InitFileParserFactory
        attributesFactory = injector.getInstance DefaultInitFileAttributesFactory
    }
}
