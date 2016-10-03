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
package com.anrisoftware.globalpom.exec.internal.scriptprocess;

import static com.anrisoftware.globalpom.exec.internal.scriptprocess.AbstractProcessExecLogger._.executed_script;
import static com.anrisoftware.globalpom.exec.internal.scriptprocess.AbstractProcessExecLogger._.executed_script_error;
import static com.anrisoftware.globalpom.exec.internal.scriptprocess.AbstractProcessExecLogger._.log_null;
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
