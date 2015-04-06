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

import static com.anrisoftware.globalpom.exec.runcommands.RunCommandsArg._.run_commands_class;
import static org.apache.commons.lang3.Validate.isInstanceOf;

import java.util.Map;

/**
 * Checks arguments for the {@link RunCommands} argument.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public class RunCommandsArg {

    private static final String RUN_COMMANDS_KEY = "runCommands";

    enum _ {

        run_commands_class("Argument '%s' must be of class %s for %s.");

        private final String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Returns the run commands collection if so specified in the arguments.
     *
     * @param args
     *            the {@link Map} arguments.
     *
     * @param parent
     *            the {@link Object} parent.
     *
     * @return the {@link RunCommands} or {@code null}.
     *
     * @throws IllegalArgumentException
     *             if the argument is not of type {@link RunCommands}.
     */
    public RunCommands runCommands(Map<String, Object> args, Object parent) {
        if (args.containsKey(RUN_COMMANDS_KEY)) {
            Object obj = args.get(RUN_COMMANDS_KEY);
            isInstanceOf(RunCommands.class, obj, run_commands_class.toString(),
                    RUN_COMMANDS_KEY, RunCommands.class, parent);
            return (RunCommands) obj;
        }
        return null;
    }

}
