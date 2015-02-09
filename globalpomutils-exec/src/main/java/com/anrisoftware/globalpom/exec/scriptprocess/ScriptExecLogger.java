/*
 * Copyright 2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.scriptprocess;

import static com.anrisoftware.globalpom.exec.scriptprocess.ScriptExecLogger._.script_done_debug;
import static com.anrisoftware.globalpom.exec.scriptprocess.ScriptExecLogger._.script_done_info;
import static com.anrisoftware.globalpom.exec.scriptprocess.ScriptExecLogger._.script_done_trace;

import java.util.Map;

import javax.inject.Inject;

import com.anrisoftware.globalpom.exec.api.ProcessTask;
import com.anrisoftware.globalpom.exec.runcommands.RunCommands;
import com.anrisoftware.globalpom.exec.runcommands.RunCommandsArg;
import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link ScriptExec}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
class ScriptExecLogger extends AbstractLogger {

    enum _ {

        script_done_trace("Script done {} for {}, {}."),

        script_done_debug("Script done {} for {}."),

        script_done_info("Script done for {}.");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final String COMMAND_KEY = "command";

    @Inject
    private RunCommandsArg runCommandsArg;

    /**
     * Sets the context of the logger to {@link ScriptExec}.
     */
    public ScriptExecLogger() {
        super(ScriptExec.class);
    }

    void scriptDone(Object parent, RunCommands runCommands, ProcessTask task,
            Map<String, Object> args, String name) {
        if (isTraceEnabled()) {
            trace(script_done_trace, args, parent, task);
        } else if (isDebugEnabled()) {
            debug(script_done_debug, args, parent);
        } else {
            info(script_done_info, parent);
        }
        if (runCommands != null) {
            if (args.containsKey(COMMAND_KEY)) {
                runCommands.add(args.get(COMMAND_KEY), args);
            } else {
                runCommands.add(name, args);
            }
        }
    }

    RunCommands runCommands(Map<String, Object> args, Object parent) {
        return runCommandsArg.runCommands(args, parent);
    }
}
