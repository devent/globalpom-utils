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

package com.anrisoftware.globalpom.exec.internal.pipeoutputs;

import static com.anrisoftware.globalpom.exec.internal.pipeoutputs.PipeOutputsModule.getPipeCommandOutputFactory;

import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.external.core.CommandOutput;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandOutput;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandOutputFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * Pipes the read data from the output of the command to a stream.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class PipeCommandOutputImpl implements PipeCommandOutput {

    /**
     * @see PipeCommandOutputFactory#create(OutputStream)
     */
    public static CommandOutput createPipeCommandOutput(OutputStream stream) {
        return getPipeCommandOutputFactory().create(stream);
    }

    @Inject
    private PipeCommandOutputFactory outputFactory;

    private InputStream stream;

    private OutputStream output;

    private int bufferSize;

    /**
     * @see PipeCommandOutputFactory#create(OutputStream)
     */
    @Inject
    PipeCommandOutputImpl(@Assisted OutputStream output) {
        this.output = output;
        this.bufferSize = 1024;
    }

    @Override
    public void setInput(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public void setPipe(OutputStream stream) {
        this.output = stream;
    }

    @Override
    public void setBufferSize(int size) {
        this.bufferSize = size;
    }

    @Override
    public CommandOutput clone() {
        CommandOutput commandOutput = outputFactory.create(output);
        commandOutput.setInput(stream);
        return commandOutput;
    }

    @Override
    public CommandOutput call() throws Exception {
        byte[] buffer = new byte[bufferSize];
        for (int s = stream.read(buffer); s != -1; s = stream.read(buffer)) {
            output.write(buffer, 0, s);
            output.flush();
        }
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
