/*
 * Copyright 2014-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.runcommands;

import static com.anrisoftware.globalpom.exec.runcommands.RunCommandsLogger._.command_added;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link RunCommands}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
class RunCommandsLogger extends AbstractLogger {

    enum _ {

        command_added("Command '{} {}' added for service '{}', {}.");

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
     * Sets the context of the logger to {@link RunCommands}.
     */
    public RunCommandsLogger() {
        super(RunCommands.class);
    }

    void commandAdded(Object command, Object args, String name, Object parent) {
        debug(command_added, command, args, name, parent);
    }
}
