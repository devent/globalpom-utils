/*
 * Copyright 2013-2023 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.version

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

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
            assert expected == it.output
        }
    }

    @Test
    void "parses version"() {
        testCases.each {
            def expected = formatFactory.create().parse(it.input)
            log.info "'{}' parses to {}", it.input, expected
            assert expected == it.version
        }
    }

    @Test
    void "compare version with upper"() {
        def testCases = [
            [version: versionFactory.create(1, 0, 0), upper: versionFactory.create(1, 0, 0), expected: 0],
            [version: versionFactory.create(2, 10, 0), upper: versionFactory.create(2, 10, Integer.MAX_VALUE), expected: -1],
        ]
        testCases.each {
            log.info "Compare {} to {} expected {}", it.version, it.upper, it.expected
            assert it.version.compareTo(it.upper) == it.expected
        }
    }

    static testCases

    static Injector injector

    static VersionFactory versionFactory

    static VersionFormatFactory formatFactory

    @BeforeAll
    static void createFactory() {
        TestUtils.toStringStyle
        this.injector = Guice.createInjector(new VersionModule())
        this.versionFactory = injector.getInstance VersionFactory
        this.formatFactory = injector.getInstance VersionFormatFactory
        this.testCases = [
            [version: versionFactory.create(1, 0, 0), input: "1.0.0", output: "1.0.0"],
            [version: versionFactory.create(1, 0, 0), input: "1.0.0", output: "1.0.0"],
            [version: versionFactory.create(0, 1, 0), input: "0.1.0", output: "0.1.0"],
            [version: versionFactory.create(0, 0, 1), input: "0.0.1", output: "0.0.1"],
            [version: versionFactory.create(1, 10, 0), input: "1.10.0", output: "1.10.0"],
            [version: versionFactory.create(1, 10, Integer.MAX_VALUE), input: "1.10", output: "1.10"],
            [version: versionFactory.create(1, Integer.MAX_VALUE, Integer.MAX_VALUE), input: "1", output: "1"],
        ]
    }
}
