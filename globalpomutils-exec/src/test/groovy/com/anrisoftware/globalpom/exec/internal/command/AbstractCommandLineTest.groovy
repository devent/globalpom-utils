/*
 * Copyright 2014-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.command

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory
import com.anrisoftware.globalpom.exec.external.core.CommandLine

import groovy.util.logging.Slf4j

/**
 * @see CommandLine
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
abstract class AbstractCommandLineTest {

    @Test
    void "add null argument"() {
        CommandLine line = commandLineFactory.create "foo"
        shouldFailWith(NullPointerException) { line.add null }
    }

    @Test
    void "add quoted argument"() {
        CommandLine line = commandLineFactory.create "foo"
        line.add "aaa"
        line.add "aaa bbb"
        line.add '"aaa ccc" bbb'

        log.info "Command line: {}", line
        assert line.executable == "foo"
        assert line.arguments[0] == 'aaa'
        assert line.arguments[1] == '"aaa bbb"'
        assert line.arguments[2] == '\'"aaa ccc" bbb\''
        assert line.command[0] == "foo"
    }

    @Test
    void "add substituted arguments"() {
        CommandLine line = commandLineFactory.create "foo"
        line.add "aaa=<xxx>"
        line.add "aaa = <xxx>"
        line.add "ddd=<zzz>"
        line.add "aaa=<xxx> bbb=<yyy>"
        line.add '"aaa ccc" bbb=<yyy>'
        line.addSub "xxx", "xxxvalue"
        line.addSub "yyy", "yyyvalue"
        line.addSub "zzz", "zzz value"

        log.info "Command line: {}", line
        assert line.executable == "foo"
        assert line.arguments[0] == 'aaa=xxxvalue'
        assert line.arguments[1] == '"aaa = xxxvalue"'
        assert line.arguments[2] == '"ddd=zzz value"'
        assert line.arguments[3] == '"aaa=xxxvalue bbb=yyyvalue"'
        assert line.arguments[4] == '\'"aaa ccc" bbb=yyyvalue\''
    }

    @Test
    void "add substituted arguments, different start, stop char"() {
        CommandLine line = commandLineFactory.create "foo"
        line.setVariableStartChar '{' as char
        line.setVariableStopChar '}' as char
        line.add "aaa={xxx}"
        line.add "aaa = {xxx}"
        line.add "ddd={zzz}"
        line.add "aaa={xxx} bbb={yyy}"
        line.add '"aaa ccc" bbb={yyy}'
        line.addSub "xxx", "xxxvalue"
        line.addSub "yyy", "yyyvalue"
        line.addSub "zzz", "zzz value"

        log.info "Command line: {}", line
        assert line.executable == "foo"
        assert line.arguments[0] == 'aaa=xxxvalue'
        assert line.arguments[1] == '"aaa = xxxvalue"'
        assert line.arguments[2] == '"ddd=zzz value"'
        assert line.arguments[3] == '"aaa=xxxvalue bbb=yyyvalue"'
        assert line.arguments[4] == '\'"aaa ccc" bbb=yyyvalue\''
    }

    abstract CommandLineFactory getCommandLineFactory()
}
