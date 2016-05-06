/*
 * Copyright 2014-2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.script;

import static com.anrisoftware.globalpom.exec.script.ScriptCommandLineLogger._.argument_null;
import static com.anrisoftware.globalpom.exec.script.ScriptCommandLineLogger._.command_line;
import static com.anrisoftware.globalpom.exec.script.ScriptCommandLineLogger._.error_copy_script;
import static com.anrisoftware.globalpom.exec.script.ScriptCommandLineLogger._.error_copy_script_message;
import static com.anrisoftware.globalpom.exec.script.ScriptCommandLineLogger._.error_process_template;
import static com.anrisoftware.globalpom.exec.script.ScriptCommandLineLogger._.error_process_template_message;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.IOException;

import com.anrisoftware.globalpom.exec.api.CommandExecException;
import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.resources.external.ResourcesException;

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

    CommandExecException errorProcessTemplate(ScriptCommandLine line,
            ResourcesException e) {
        return logException(
                new CommandExecException(error_process_template, e).add(
                        command_line, line), error_process_template_message,
                line.getTemplate().getName());
    }

    CommandExecException errorCopyScript(ScriptCommandLine line, IOException e) {
        return logException(new CommandExecException(error_copy_script, e).add(
                command_line, line), error_copy_script_message, line
                .getTemplate().getName());
    }

    void checkArgument(ScriptCommandLine line, Object argument) {
        notNull(argument, argument_null.toString(), line);
    }
}
