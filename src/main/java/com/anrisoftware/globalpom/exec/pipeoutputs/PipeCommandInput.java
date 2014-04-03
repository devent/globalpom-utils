/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.pipeoutputs;

import static com.anrisoftware.globalpom.exec.pipeoutputs.PipeOutputsModule.getPipeCommandInputFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandInput;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;

/**
 * Pipes the read data from one stream to the standard input of the command.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class PipeCommandInput implements CommandInput {

    /**
     * @see PipeCommandInputFactory#create(InputStream)
     */
    public static PipeCommandInput createPipeCommandInput(InputStream stream) {
        return getPipeCommandInputFactory().create(stream);
    }

    /**
     * Pipes the string content to the standard input of the command.
     * 
     * @param string
     *            the {@link String}.
     * 
     * @return the {@link PipeCommandInput}.
     */
    public static PipeCommandInput fromString(String string) {
        return fromString(Guice.createInjector(new PipeOutputsModule()), string);
    }

    /**
     * Pipes the string content to the standard input of the command.
     * 
     * @param injector
     *            the Guice {@link Injector}.
     * 
     * @param string
     *            the {@link String}.
     * 
     * @return the {@link PipeCommandInput}.
     */
    public static PipeCommandInput fromString(Injector injector, String string) {
        InputStream stream = new ByteArrayInputStream(string.getBytes());
        return injector.getInstance(PipeCommandInputFactory.class).create(
                stream);
    }

    @Inject
    private PipeCommandInputFactory inputFactory;

    private OutputStream stream;

    private InputStream input;

    private int bufferSize;

    /**
     * @see PipeCommandInputFactory#create(InputStream)
     */
    @Inject
    PipeCommandInput(@Assisted InputStream stream) {
        this.input = stream;
        this.bufferSize = 1024;
    }

    @Override
    public void setOutput(OutputStream stream) {
        this.stream = stream;
    }

    /**
     * Sets the stream that is read and piped to the standard input of the
     * command.
     * 
     * @param stream
     *            the {@link InputStream}.
     */
    public void setPipe(InputStream stream) {
        this.input = stream;
    }

    /**
     * Sets the buffer size.
     * 
     * @param size
     *            the size in bytes.
     */
    public void setBufferSize(int size) {
        this.bufferSize = size;
    }

    @Override
    public PipeCommandInput clone() {
        PipeCommandInput commandInput = inputFactory.create(input);
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
