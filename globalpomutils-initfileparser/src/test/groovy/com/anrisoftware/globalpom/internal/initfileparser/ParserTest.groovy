/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.internal.initfileparser

import static com.anrisoftware.globalpom.utils.TestUtils.*

import javax.inject.Inject

import org.junit.Before
import org.junit.Test

import com.anrisoftware.globalpom.external.initfileparser.DefaultInitFileAttributesFactory
import com.anrisoftware.globalpom.external.initfileparser.InitFileParserFactory
import com.anrisoftware.globalpom.external.initfileparser.SectionFactory
import com.anrisoftware.globalpom.external.initfileparser.SectionFormatterFactory
import com.google.inject.Guice

/**
 * @see InitFileParser
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ParserTest extends AbstractParserTest {

    @Test
    void "parse INI-file"() {
        super."parse INI-file"()
    }

    @Test
    void "parse INI-file, no section"() {
        super."parse INI-file, no section"()
    }

    @Test
    void "parse INI-file, multi-line property"() {
        super."parse INI-file, multi-line property"()
    }

    @Test
    void "parse piwik INI-file"() {
        super."parse piwik INI-file"()
    }

    @Test
    void "format section"() {
        super."format section"()
    }

    @Test
    void "format section, no string quote"() {
        super."format section, no string quote"()
    }

    @Test
    void "format multi-value section"() {
        super."format multi-value section"()
    }

    DefaultInitFileAttributesFactory getAttributesFactory() {
        attributesFactory
    }

    InitFileParserFactory getParserFactory() {
        parserFactory
    }

    SectionFormatterFactory getSectionFormatterFactory() {
        sectionFormatterFactory
    }

    SectionFactory getSectionFactory() {
        sectionFactory
    }

    @Inject
    InitFileParserFactory parserFactory

    @Inject
    DefaultInitFileAttributesFactory attributesFactory

    @Inject
    SectionFormatterFactory sectionFormatterFactory

    @Inject
    SectionFactory sectionFactory

    @Before
    void setup() {
        toStringStyle
        Guice.createInjector(new InitFileParserModule()).injectMembers(this)
    }
}
