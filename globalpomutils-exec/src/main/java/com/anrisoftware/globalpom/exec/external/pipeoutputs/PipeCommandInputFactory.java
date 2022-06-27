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
package com.anrisoftware.globalpom.exec.external.pipeoutputs;

import java.io.InputStream;

import com.anrisoftware.globalpom.exec.external.core.CommandInput;

/**
 * Factory to create the pipe input.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface PipeCommandInputFactory {

    /**
     * Creates the pipe input that reads the specified stream to the standard
     * input of the command.
     *
     * @param stream
     *            the {@link InputStream}.
     *
     * @return the {@link CommandInput}.
     */
    CommandInput create(InputStream stream);

    /**
     * Pipes the string content to the standard input of the command.
     *
     * @param string
     *            the {@link String}.
     *
     * @return the {@link CommandInput}.
     */
    CommandInput fromString(String string);
}
