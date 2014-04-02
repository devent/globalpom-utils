package com.anrisoftware.globalpom.exec.command;

import static com.anrisoftware.globalpom.exec.command.CommandLineLogger._.argument_null;
import static org.apache.commons.lang3.Validate.notNull;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link CommandLine}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
class CommandLineLogger extends AbstractLogger {

    enum _ {

        argument_null("Command line argument must be set for %s.");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Sets the context of the logger to {@link CommandLine}.
     */
    public CommandLineLogger() {
        super(CommandLine.class);
    }

    void checkArgument(CommandLine line, Object argument) {
        notNull(argument, argument_null.toString(), line);
    }
}
