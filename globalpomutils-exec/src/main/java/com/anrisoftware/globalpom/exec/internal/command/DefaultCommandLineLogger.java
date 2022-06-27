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
package com.anrisoftware.globalpom.exec.internal.command;


import static com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLineLogger.m.argumentmnull;
import static org.apache.commons.lang3.Validate.notNull;

import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link DefaultCommandLine}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
class DefaultCommandLineLogger extends AbstractLogger {

    enum m {

        argumentmnull("Command line argument must be set for %s.");

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
     * Sets the context of the logger to {@link DefaultCommandLine}.
     */
    public DefaultCommandLineLogger() {
        super(DefaultCommandLine.class);
    }

    void checkArgument(CommandLine line, Object argument) {
        notNull(argument, argumentmnull.toString(), line);
    }
}
