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
