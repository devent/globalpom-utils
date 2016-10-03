/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.exec.internal.script.ScriptCommandLineLogger._.argument_null;
import static org.apache.commons.lang3.Validate.notNull;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link ScriptCommandLine}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
class ScriptCommandLineLogger extends AbstractLogger {

    enum _ {

        error_process_template("Error process template"),

        error_process_template_message("Error process template '{}'."),

        command_line("command line"),

        error_copy_script("Error copy script"),

        error_copy_script_message("Error copy script for template '{}'."),

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
     * Sets the context of the logger to {@link ScriptCommandLine}.
     */
    public ScriptCommandLineLogger() {
        super(ScriptCommandLine.class);
    }

    void checkArgument(ScriptCommandLine line, Object argument) {
        notNull(argument, argument_null.toString(), line);
    }
}
