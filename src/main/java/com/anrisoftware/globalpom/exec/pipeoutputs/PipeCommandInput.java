package com.anrisoftware.globalpom.exec.pipeoutputs;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandInput;

public class PipeCommandInput implements CommandInput {

    private OutputStream stream;

    private InputStream input;

    @Override
    public void setOutput(OutputStream stream) {
        this.stream = stream;
    }

    public void setPipe(InputStream stream) {
        this.input = stream;
    }

    @Override
    public PipeCommandInput clone() {
        return this;
    }

    @Override
    public CommandInput call() throws Exception {
        int b;
        while ((b = input.read()) != -1) {
            stream.write(b);
        }
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
