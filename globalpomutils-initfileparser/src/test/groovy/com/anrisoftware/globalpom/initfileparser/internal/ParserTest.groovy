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
package com.anrisoftware.globalpom.initfileparser.internal

import static com.anrisoftware.globalpom.utils.TestUtils.*

import javax.inject.Inject

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.initfileparser.external.DefaultInitFileAttributesFactory
import com.anrisoftware.globalpom.initfileparser.external.InitFileParserFactory
import com.anrisoftware.globalpom.initfileparser.external.SectionFactory
import com.anrisoftware.globalpom.initfileparser.external.SectionFormatterFactory
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

    @BeforeEach
    void setup() {
        toStringStyle
        Guice.createInjector(new InitFileParserModule()).injectMembers(this)
    }
}
