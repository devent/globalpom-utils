/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import com.anrisoftware.globalpom.initfileparser.external.DefaultInitFileAttributesFactory
import com.anrisoftware.globalpom.initfileparser.external.DefaultInitFileAttributesService
import com.anrisoftware.globalpom.initfileparser.external.InitFileParserFactory
import com.anrisoftware.globalpom.initfileparser.external.InitFileParserService
import com.anrisoftware.globalpom.initfileparser.external.SectionFactory
import com.anrisoftware.globalpom.initfileparser.external.SectionFormatterFactory
import com.anrisoftware.globalpom.initfileparser.external.SectionFormatterService
import com.anrisoftware.globalpom.initfileparser.external.SectionService

/**
 * @see DefaultCommandLineServiceImpl
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
class InitFileParserServiceTest extends AbstractParserTest {

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

    @Rule
    public final OsgiContext context = new OsgiContext()

    DefaultInitFileAttributesFactory getAttributesFactory() {
        attributesService
    }

    InitFileParserFactory getParserFactory() {
        parserService
    }

    SectionFormatterFactory getSectionFormatterFactory() {
        sectionFormatterService
    }

    SectionFactory getSectionFactory() {
        sectionService
    }

    @Inject
    InitFileParserService parserService

    @Inject
    DefaultInitFileAttributesService attributesService

    @Inject
    SectionFormatterService sectionFormatterService

    @Inject
    SectionService sectionService

    @Before
    void createFactories() {
        toStringStyle
        this.parserService = context.registerInjectActivateService(new InitFileParserServiceImpl(), null)
        this.attributesService = context.registerInjectActivateService(new DefaultInitFileAttributesServiceImpl(), null)
        this.sectionFormatterService = context.registerInjectActivateService(new SectionFormatterServiceImpl(), null)
        this.sectionService = context.registerInjectActivateService(new SectionServiceImpl(), null)
    }
}
