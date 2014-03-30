package com.anrisoftware.globalpom.exec

import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.exec.command.CommandLine
import com.anrisoftware.globalpom.exec.command.CommandLineFactory
import com.anrisoftware.globalpom.exec.command.CommandLineModule
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see CommandLine
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Slf4j
class CommandLineTest {

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
        line.sub "xxx", "xxxvalue"
        line.sub "yyy", "yyyvalue"
        line.sub "zzz", "zzz value"

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
        line.sub "xxx", "xxxvalue"
        line.sub "yyy", "yyyvalue"
        line.sub "zzz", "zzz value"

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
        injector = Guice.createInjector(new CommandLineModule())
        commandLineFactory = injector.getInstance CommandLineFactory
    }
}
