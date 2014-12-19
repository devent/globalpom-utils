/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.version

import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see VersionFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
@Slf4j
class VersionFormatTest {

    @Test
    void "format version"() {
        testCases.each {
            def expected = formatFactory.create().format(it.version)
            log.info "{} format to '{}'", it.version, expected
            assert expected == it.format
        }
    }

    @Test
    void "parses version"() {
        testCases.each {
            def expected = formatFactory.create().parse(it.format)
            log.info "'{}' parses to {}", it.format, expected
            assert expected == it.version
        }
    }

    static testCases

    static Injector injector

    static VersionFactory versionFactory

    static VersionFormatFactory formatFactory

    @BeforeClass
    static void createFactory() {
        TestUtils.toStringStyle
        this.injector = Guice.createInjector(new VersionModule())
        this.versionFactory = injector.getInstance VersionFactory
        this.formatFactory = injector.getInstance VersionFormatFactory
        this.testCases = [
            [version: versionFactory.create(1, 0, 0), format: "1.0.0"],
            [version: versionFactory.create(0, 1, 0), format: "0.1.0"],
            [version: versionFactory.create(0, 0, 1), format: "0.0.1"],
            [version: versionFactory.create(1, 10, 0), format: "1.10.0"],
            [version: versionFactory.create(1, 10, Integer.MAX_VALUE), format: "1.10"],
            [version: versionFactory.create(1, Integer.MAX_VALUE, Integer.MAX_VALUE), format: "1"],
        ]
    }
}
