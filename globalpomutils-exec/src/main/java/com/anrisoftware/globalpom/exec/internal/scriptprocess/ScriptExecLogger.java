package com.anrisoftware.globalpom.exec.internal.scriptprocess;

/*-
 * #%L
 * Global POM Utilities :: Exec
 * %%
 * Copyright (C) 2014 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static com.anrisoftware.globalpom.exec.internal.scriptprocess.ScriptExecLogger.m.scriptmdonemdebug;
import static com.anrisoftware.globalpom.exec.internal.scriptprocess.ScriptExecLogger.m.scriptmdoneminfo;
import static com.anrisoftware.globalpom.exec.internal.scriptprocess.ScriptExecLogger.m.scriptmdonemtrace;

import java.util.Map;

import javax.inject.Inject;

import com.anrisoftware.globalpom.exec.external.core.ProcessTask;
import com.anrisoftware.globalpom.exec.internal.runcommands.RunCommands;
import com.anrisoftware.globalpom.exec.internal.runcommands.RunCommandsArg;
import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link ScriptExecImpl}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
class ScriptExecLogger extends AbstractLogger {

    enum m {

        scriptmdonemtrace("Script done {} for {}, {}."),

        scriptmdonemdebug("Script done {} for {}."),

        scriptmdoneminfo("Script done for {}.");

        private String name;

        private m(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static final String COMMANDmKEY = "command";

    @Inject
    private RunCommandsArg runCommandsArg;

    /**
     * Sets the context of the logger to {@link ScriptExecImpl}.
     */
    public ScriptExecLogger() {
        super(ScriptExecImpl.class);
    }

    void scriptDone(Object parent, RunCommands runCommands, ProcessTask task, Map<String, Object> args, String name) {
        if (isTraceEnabled()) {
            trace(scriptmdonemtrace, args, parent, task);
        } else if (isDebugEnabled()) {
            debug(scriptmdonemdebug, args, parent);
        } else {
            info(scriptmdoneminfo, parent);
        }
        if (runCommands != null) {
            if (args.containsKey(COMMANDmKEY)) {
                runCommands.add(args.get(COMMANDmKEY), args);
            } else {
                runCommands.add(name, args);
            }
        }
    }

    RunCommands runCommands(Map<String, Object> args, Object parent) {
        return runCommandsArg.runCommands(args, parent);
    }
}
