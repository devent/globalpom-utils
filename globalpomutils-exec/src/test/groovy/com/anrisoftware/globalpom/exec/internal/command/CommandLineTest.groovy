package com.anrisoftware.globalpom.exec.internal.command

import static com.anrisoftware.globalpom.utils.TestUtils.*

import com.anrisoftware.globalpom.exec.external.command.CommandLineFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see CommandLine
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
class CommandLineTest extends AbstractCommandLineTest {

    CommandLineFactory getCommandLineFactory() {
        commandLineFactory
    }

    static Injector injector

    static CommandLineFactory commandLineFactory

    @BeforeAll
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new DefaultCommandLineModule())
        commandLineFactory = injector.getInstance CommandLineFactory
    }
}
