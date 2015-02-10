/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-initfileparser.
 *
 * globalpomutils-initfileparser is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-initfileparser is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-initfileparser. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.initfileparser

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.BeforeClass
import org.junit.Test

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

    @Test
    void "parse INI-file, multi-line property"() {
        def attributes = attributesFactory.create()
        def parser = parserFactory.create(inifileMultiLine, attributes)()
        List sections = parser.inject([]) { acc, val -> acc << val }
        assert sections.size() == 4
        assert sections[0].name == "ssh"
        assert sections[0].properties.size() == 6
        assert sections[0].properties.getProperty("enabled") == "true"
        assert sections[0].properties.getProperty("port") == "ssh"
        assert sections[0].properties.getProperty("filter") == "sshd"
        assert sections[0].properties.getProperty("logpath") == "/var/log/auth.log"
        assert sections[0].properties.getProperty("maxretry") == "6"
        assert sections[0].properties.getProperty("action_mw") == "%(banaction)s[name=%(__name__)s, port=\"%(port)s\", protocol=\"%(protocol)s]%(mta)s-whois[name=%(__name__)s, dest=\"%(destemail)s\", protocol=\"%(protocol)s]"
    }

    @Test
    void "parse piwik INI-file"() {
        def attributes = attributesFactory.create()
        attributes.comment = ';'
        def parser = parserFactory.create(inifilePiwikConfig, attributes)()
        List sections = parser.inject([]) { acc, val -> acc << val }
        assert sections.size() == 4
        assert sections[0].name == "database"
        assert sections[0].properties.size() == 7
        assert sections[0].properties["host"] == "localhost"
        assert sections[0].properties["username"] == "piwik"
        assert sections[1].name == "General"
        assert sections[1].properties.size() == 3
        assert sections[1].properties["proxy_client_headers"].size() == 1
        assert sections[1].properties["proxy_client_headers"].containsAll(["HTTP_X_FORWARDED_FOR"])
        assert sections[1].properties["trusted_hosts"].size() == 2
        assert sections[1].properties["trusted_hosts"].containsAll([
            "anrisoftware.com",
            "www.mueller-public.de"
        ])
        assert sections[2].name == "PluginsInstalled"
        assert sections[2].properties.size() == 1
        assert sections[2].properties["PluginsInstalled"].size() == 47
        assert sections[3].name == "Plugins_Tracker"
        assert sections[3].properties.size() == 1
        assert sections[3].properties["Plugins_Tracker"].size() == 7
    }

    @Test
    void "format section"() {
        def attributes = attributesFactory.create()
        def formatter = sectionFormatterFactory.create(attributes)
        def name = "Foo"
        def properties = new Properties()
        properties.setProperty "value_a", "a"
        properties.setProperty "value_b", "b"
        properties.setProperty "value_c", "foo bar"
        def section = sectionFactory.create(name, properties)
        def str = formatter.format section
        assertStringContent str,
                """[Foo]
value_c = "foo bar"
value_b = b
value_a = a
"""
    }

    @Test
    void "format multi-value section"() {
        def attributes = attributesFactory.create()
        def formatter = sectionFormatterFactory.create(attributes)
        def name = "Foo"
        def properties = new Properties()
        properties.put "value_a", ["a", "b"]
        properties.setProperty "value_b", "b"
        def section = sectionFactory.create(name, properties)
        def str = formatter.format section
        assertStringContent str,
                """[Foo]
value_b = b
value_a[] = a
value_a[] = b
"""
    }

    static URL inifile = ParserTest.class.getResource("inifile.txt")

    static URL inifileNoSection = ParserTest.class.getResource("inifile_no_section.txt")

    static URL inifileMultiLine = ParserTest.class.getResource("inifile_multiline.txt")

    static URL inifilePiwikConfig = ParserTest.class.getResource("piwik_config_ini_php.txt")

    static Injector injector

    static InitFileParserFactory parserFactory

    static DefaultInitFileAttributesFactory attributesFactory

    static SectionFormatterFactory sectionFormatterFactory

    static SectionFactory sectionFactory

    @BeforeClass
    static void createFactory() {
        toStringStyle
        injector = Guice.createInjector(new InitFileParserModule())
        parserFactory = injector.getInstance InitFileParserFactory
        attributesFactory = injector.getInstance DefaultInitFileAttributesFactory
        sectionFormatterFactory = injector.getInstance SectionFormatterFactory
        sectionFactory = injector.getInstance SectionFactory
    }
}
