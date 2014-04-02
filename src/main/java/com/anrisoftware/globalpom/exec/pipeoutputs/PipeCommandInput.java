package com.anrisoftware.globalpom.exec.pipeoutputs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandInput;
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
