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
package com.anrisoftware.globalpom.exec.external.logoutputs;

import org.slf4j.Logger;

import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.CommandOutput;

/**
 * Factory to create the logger output that logs the output as debug level.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public interface DebugLogCommandOutputFactory {

    /**
     * Creates the logger output that logs the output as debug level.
     *
     * @param logger
     *            the {@link Logger}.
     *
     * @param commandLine
     *            the the {@link CommandLine} for the logging message.
     *
     * @return the {@link CommandOutput}.
     */
    CommandOutput create(Logger logger, CommandLine commandLine);
}
