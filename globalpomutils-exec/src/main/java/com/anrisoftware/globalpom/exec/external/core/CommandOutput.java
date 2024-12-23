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

import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * Reads the standard output of the executed command.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface CommandOutput extends Callable<CommandOutput> {

    /**
     * Sets the stream that is connected to output of the executed command.
     * 
     * @param stream
     *            the {@link InputStream}.
     */
    void setInput(InputStream stream);

    /**
     * Clones the standard output reader. The cloned reader have the same
     * connected input stream.
     * 
     * @return the cloned {@link CommandOutput}.
     */
    CommandOutput clone();

    /**
     * Reads the standard output from the connected stream.
     */
    @Override
    CommandOutput call() throws Exception;
}
