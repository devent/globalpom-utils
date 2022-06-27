/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.script;


import static com.anrisoftware.globalpom.exec.internal.script.ScriptCommandLineLogger.m.argumentmnull;
import static com.anrisoftware.globalpom.exec.internal.script.ScriptCommandLineLogger.m.errorSetExecutable;
import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.File;
import java.io.IOException;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link ScriptCommandLine}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
class ScriptCommandLineLogger extends AbstractLogger {

    enum m {

        errormprocessmtemplate("Error process template"),

        errormprocessmtemplatemmessage("Error process template '{}'."),

        commandmline("command line"),

        errormcopymscript("Error copy script"),

        errormcopymscriptmmessage("Error copy script for template '{}'."),

        argumentmnull("Command line argument must be set for %s."),

        errorSetExecutable("Could not set executable script: %s");

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
     * Sets the context of the logger to {@link ScriptCommandLine}.
     */
    public ScriptCommandLineLogger() {
        super(ScriptCommandLine.class);
    }

    void checkArgument(ScriptCommandLine line, Object argument) {
        notNull(argument, argumentmnull.toString(), line);
    }

    IOException errorSetExecutable(ScriptCommandLine scriptCommandLine, File file) {
        IOException ex = new IOException(format(errorSetExecutable.toString(), file.toString()));
        log.error(ex.getMessage(), ex);
        return ex;
    }
}
