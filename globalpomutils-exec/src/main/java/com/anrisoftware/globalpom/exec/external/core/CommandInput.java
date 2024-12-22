/*
 * Copyright 2014-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.external.core;

import java.io.OutputStream;
import java.util.concurrent.Callable;

/**
 * Reads the standard output of the executed command.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface CommandInput extends Callable<CommandInput> {

    /**
     * Sets the stream that is connected to the standard input of the executed
     * command.
     * 
     * @param stream
     *            the {@link OutputStream}.
     */
    void setOutput(OutputStream stream);

    /**
     * Clones the standard input writer. The cloned writer have the same
     * connected output stream.
     * 
     * @return the cloned {@link CommandInput}.
     */
    CommandInput clone();

    /**
     * Writes to the standard input to the connected stream.
     */
    @Override
    CommandInput call() throws Exception;
}
