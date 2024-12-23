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
package com.anrisoftware.globalpom.exec.internal.runcommands;


import static com.anrisoftware.globalpom.exec.internal.runcommands.RunCommandsLogger.m.commandmadded;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link RunCommands}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
class RunCommandsLogger extends AbstractLogger {

    enum m {

        commandmadded("Command '{} {}' added for service '{}', {}.");

        private String name;

        private m(String name) {
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
        debug(commandmadded, command, args, name, parent);
    }
}
