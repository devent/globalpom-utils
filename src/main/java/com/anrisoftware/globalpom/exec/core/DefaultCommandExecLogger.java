/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.core;

import static com.anrisoftware.globalpom.exec.core.DefaultCommandExecLogger._.command_line;
import static com.anrisoftware.globalpom.exec.core.DefaultCommandExecLogger._.error_exec_command;
import static com.anrisoftware.globalpom.exec.core.DefaultCommandExecLogger._.error_exec_command_message;
import static com.anrisoftware.globalpom.exec.core.DefaultCommandExecLogger._.exec_;

import java.io.IOException;

import com.anrisoftware.globalpom.exec.api.CommandExecException;
import com.anrisoftware.globalpom.exec.command.CommandLine;
import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link DefaultCommandExec}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
class DefaultCommandExecLogger extends AbstractLogger {

    enum _ {

        error_exec_command("Error exec command"),

        error_exec_command_message("Error exec command '{}'."),

        exec_("exec"),

        command_line("command");

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
     * Sets the context of the logger to {@link DefaultCommandExec}.
     */
    public DefaultCommandExecLogger() {
        super(DefaultCommandExec.class);
    }

    CommandExecException errorExecuteCommand(DefaultCommandExec exec,
            IOException e, CommandLine commandLine) throws CommandExecException {
        throw logException(
                new CommandExecException(error_exec_command, e)
                        .add(exec_, exec).add(command_line, commandLine),
                error_exec_command_message, commandLine.getCommand());
    }
}
