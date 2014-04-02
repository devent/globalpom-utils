package com.anrisoftware.globalpom.exec.pipeoutputs;

import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.google.inject.assistedinject.Assisted;

public class PipeCommandOutput implements CommandOutput {

    private static final int BUFFER_SIZE = 0;

    @Inject
    private PipeCommandOutputFactory outputFactory;

    private InputStream stream;

    private OutputStream output;

    /**
     * @see PipeCommandOutputFactory#create(OutputStream)
     */
    @Inject
    PipeCommandOutput(@Assisted OutputStream output) {
        this.output = output;
    }

    @Override
    public void setInput(InputStream stream) {
        this.stream = stream;
    }

    public void setPipe(OutputStream stream) {
        this.output = stream;
    }

    @Override
    public PipeCommandOutput clone() {
        PipeCommandOutput commandOutput = outputFactory.create(output);
        commandOutput.setInput(stream);
        return commandOutput;
    }

    @Override
    public CommandOutput call() throws Exception {
        byte[] buffer = new byte[BUFFER_SIZE];
        int b;
        while ((b = stream.read()) != -1) {
            output.write(b);
        }
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
