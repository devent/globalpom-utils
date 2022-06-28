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
package com.anrisoftware.globalpom.exec.internal.pipeoutputs;

import static com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeOutputsModule.getPipeCommandInputFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.external.core.CommandInput;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInput;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInputFactory;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Pipes the read data from one stream to the standard input of the command.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class PipeCommandInputImpl implements PipeCommandInput {

    /**
     * @see PipeCommandInputFactory#create(InputStream)
     *
     * @param stream the {@link InputStream}
     *
     * @return the {@link CommandInput}
     */
    public static CommandInput createPipeCommandInput(InputStream stream) {
        return getPipeCommandInputFactory().create(stream);
    }

    @Inject
    private PipeCommandInputFactory inputFactory;

    private OutputStream stream;

    private InputStream input;

    private int bufferSize;

    /**
     * @see PipeCommandInputFactory#create(InputStream)
     */
    @AssistedInject
    PipeCommandInputImpl(@Assisted InputStream stream) {
        this.input = stream;
        this.bufferSize = 1024;
    }

    /**
     * @see PipeCommandInputFactory#fromString(String)
     */
    @AssistedInject
    PipeCommandInputImpl(@Assisted String string) {
        this.input = new ByteArrayInputStream(string.getBytes());
        this.bufferSize = 1024;
    }

    @Override
    public void setOutput(OutputStream stream) {
        this.stream = stream;
    }

    @Override
    public void setPipe(InputStream stream) {
        this.input = stream;
    }

    @Override
    public void setBufferSize(int size) {
        this.bufferSize = size;
    }

    @Override
    public CommandInput clone() {
        CommandInput commandInput = inputFactory.create(input);
        commandInput.setOutput(stream);
        return commandInput;
    }

    @Override
    public CommandInput call() throws Exception {
        byte[] buffer = new byte[bufferSize];
        for (int s = input.read(buffer); s != -1; s = input.read(buffer)) {
            stream.write(buffer, 0, s);
            stream.flush();
        }
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
