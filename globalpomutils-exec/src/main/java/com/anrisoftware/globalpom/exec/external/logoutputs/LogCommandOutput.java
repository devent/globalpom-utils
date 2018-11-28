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

package com.anrisoftware.globalpom.exec.external.logoutputs;

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

import java.io.InputStream;

import org.slf4j.Logger;

import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.CommandOutput;

/**
 * Pipes the read data from the output of the command to a logger.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
public interface LogCommandOutput extends CommandOutput {

    /**
     * Returns the stream that is connected to output of the executed command.
     *
     * @return the {@link InputStream}.
     */
    InputStream getInput();

    /**
     * Sets the buffer size.
     *
     * @param size
     *            the size in bytes.
     */
    void setBufferSize(int size);

    /**
     * Sets the logger that is filled with the piped to the output of the
     * command.
     *
     * @param logger
     *            the {@link Logger}.
     */
    void setLogger(Logger logger);

    /**
     * Returns the logger that is filled with the piped to the output of the
     * command.
     *
     * @return the {@link Logger}.
     */
    Logger getLogger();

    /**
     * Returns the command line for the logging message.
     *
     * @return the {@link CommandLine}.
     */
    CommandLine getCommandLine();

    /**
     * Returns the command for the logging message.
     *
     * @return the {@link String} command.
     */
    String getCommand();

    /**
     * Sets the message string.
     *
     * @param message
     *            the {@link String} message.
     */
    void setMessage(String message);

    /**
     * Returns the message string.
     *
     * @return the {@link String} message.
     */
    String getMessage();

}
