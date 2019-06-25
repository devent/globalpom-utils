/**
 * Copyright © 2014 Erwin Müller (erwin.mueller@anrisoftware.com)
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


import static com.anrisoftware.globalpom.exec.internal.runcommands.RunCommandsArg.m.runmcommandsmclass;
import static org.apache.commons.lang3.Validate.isInstanceOf;

import java.util.Map;

import javax.inject.Inject;

import com.anrisoftware.globalpom.exec.external.runcommands.RunCommandsFactory;

/**
 * Checks arguments for the {@link RunCommands} argument.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public class RunCommandsArg {

    @Inject
    private RunCommandsFactory runCommandsFactory;

    /**
     * Returns the run commands collection if so specified in the arguments.
     *
     * @param args   the {@link Map} arguments.
     *
     * @param parent the {@link Object} parent.
     *
     * @return the {@link RunCommands}.
     *
     * @throws IllegalArgumentException if the argument is not of type
     *                                  {@link RunCommands}.
     */
    public RunCommands runCommands(Map<String, Object> args, Object parent) {
        if (args.containsKey(RUNmCOMMANDSmKEY)) {
            Object obj = args.get(RUNmCOMMANDSmKEY);
            isInstanceOf(RunCommands.class, obj, runmcommandsmclass.toString(), RUNmCOMMANDSmKEY, RunCommands.class,
                    parent);
            return (RunCommands) obj;
        }
        return runCommandsFactory.create(parent, "default");
    }

    private static final String RUNmCOMMANDSmKEY = "runCommands";

    enum m {

        runmcommandsmclass("Argument '%s' must be of class %s for %s.");

        private final String name;

        private m(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
