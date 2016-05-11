/*
 * Copyright 2014-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-exec.
 *
 * globalpomutils-exec is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-exec is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-exec. If not, see <http://www.gnu.org/licenses/>.
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
