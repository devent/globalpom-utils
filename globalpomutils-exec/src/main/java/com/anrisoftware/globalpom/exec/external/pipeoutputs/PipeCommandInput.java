/*
 * Copyright 2014-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
 * Pipes the read data from one stream to the standard input of the command.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
public interface PipeCommandInput extends CommandInput {

    /**
     * Sets the stream that is read and piped to the standard input of the
     * command.
     *
     * @param stream
     *            the {@link InputStream}.
     */
    void setPipe(InputStream stream);

    /**
     * Sets the buffer size.
     *
     * @param size
     *            the size in bytes.
     */
    void setBufferSize(int size);

}
