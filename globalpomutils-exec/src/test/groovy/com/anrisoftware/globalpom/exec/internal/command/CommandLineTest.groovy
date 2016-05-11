/*
 * Copyright 2014-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-exec.
 *
 * globalpomutils-exec is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-exec is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-exec. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.internal.command

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory
import com.anrisoftware.globalpom.exec.external.core.CommandLine
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see CommandLine
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
@Slf4j
class CommandLineTest {

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

    static Injector injector

    static CommandLineFactory commandLineFactory

    @BeforeClass
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new DefaultCommandLineModule())
        commandLineFactory = injector.getInstance CommandLineFactory
    }
}
