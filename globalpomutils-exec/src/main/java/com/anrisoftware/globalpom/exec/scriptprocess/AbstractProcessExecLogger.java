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
package com.anrisoftware.globalpom.exec.scriptprocess;

import static com.anrisoftware.globalpom.exec.scriptprocess.AbstractProcessExecLogger._.executed_script;
import static com.anrisoftware.globalpom.exec.scriptprocess.AbstractProcessExecLogger._.executed_script_error;
import static com.anrisoftware.globalpom.exec.scriptprocess.AbstractProcessExecLogger._.log_null;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link AbstractProcessExec}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
class AbstractProcessExecLogger extends AbstractLogger {

    private static final String LOG_ARG = "log";

    enum _ {

        log_null("Logger argument '%s' must be set"),

        executed_script("Executed script {}: \n{}"),

        executed_script_error("Error read script for {}.");

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
     * Sets the context of the logger to {@link AbstractProcessExec}.
     */
    public AbstractProcessExecLogger() {
        super(AbstractProcessExec.class);
    }

    void checkArgs(AbstractProcessExec exec, Map<String, Object> args) {
        notNull(args.get(LOG_ARG), log_null.toString(), LOG_ARG);
    }

    void executedScript(AbstractProcessExec exec, String script) {
        if (isTraceEnabled()) {
            try {
                File file = new File(script);
                String string = readFileToString(file);
                trace(executed_script, exec, string);
            } catch (IOException e) {
                logException(e, executed_script_error, exec);
            }
        }
    }

}
