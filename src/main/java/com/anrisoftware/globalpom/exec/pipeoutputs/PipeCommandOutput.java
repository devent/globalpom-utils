package com.anrisoftware.globalpom.exec.pipeoutputs;

import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.google.inject.assistedinject.Assisted;

/**
 * Pipes the read data from the output of the command to a stream.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class PipeCommandOutput implements CommandOutput {

    @Inject
    private PipeCommandOutputFactory outputFactory;

    private InputStream stream;

    private OutputStream output;

    private int bufferSize;

    /**
     * @see PipeCommandOutputFactory#create(OutputStream)
     */
    @Inject
    PipeCommandOutput(@Assisted OutputStream output) {
        this.output = output;
        this.bufferSize = 1024;
    }

    @Override
    public void setInput(InputStream stream) {
        this.stream = stream;
    }

    /**
     * Sets the stream that is filled with the piped to the output of the
     * command.
     * 
     * @param stream
     *            the {@link OutputStream}.
     */
    public void setPipe(OutputStream stream) {
        this.output = stream;
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
    public PipeCommandOutput clone() {
        PipeCommandOutput commandOutput = outputFactory.create(output);
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
