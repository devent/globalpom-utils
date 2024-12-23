/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
 * @see Version
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
@Slf4j
class VersionTest {

    @Test
    void "compare versions"() {
        testCases.each {
            def lhs = factory.create it.lhs.major, it.lhs.minor, it.lhs.rev
            def rhs = factory.create it.rhs.major, it.rhs.minor, it.rhs.rev
            def expected = lhs.compareTo(rhs)
            log.info "Compare {} and {} => {}", lhs, rhs, expected
            assert expected == it.compared
        }
    }

    static testCases

    static Injector injector

    static VersionFactory factory

    @BeforeAll
    static void createFactory() {
        TestUtils.toStringStyle
        this.injector = Guice.createInjector(new VersionModule())
        this.factory = injector.getInstance VersionFactory
        this.testCases = [
            [lhs: [major: 1, minor: 0, rev: 0], rhs: [major: 1, minor: 0, rev: 0], compared: 0],
            [lhs: [major: 2, minor: 0, rev: 0], rhs: [major: 1, minor: 0, rev: 0], compared: 1],
            [lhs: [major: 1, minor: 5, rev: 0], rhs: [major: 1, minor: 0, rev: 0], compared: 1],
            [lhs: [major: 1, minor: 0, rev: 1], rhs: [major: 1, minor: 0, rev: 0], compared: 1],
            [lhs: [major: 1, minor: 10, rev: 1], rhs: [major: 1, minor: 12, rev: 3], compared: -1],
            [lhs: [major: 1, minor: 10, rev: 1], rhs: [major: 1, minor: 10, rev: 3], compared: -1],
        ]
    }
}
