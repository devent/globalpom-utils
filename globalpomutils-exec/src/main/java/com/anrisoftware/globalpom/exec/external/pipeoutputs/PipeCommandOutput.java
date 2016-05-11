package com.anrisoftware.globalpom.exec.external.pipeoutputs;

import java.io.OutputStream;

import com.anrisoftware.globalpom.exec.external.core.CommandOutput;

/**
 * Pipes the read data from the output of the command to a stream.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
public interface PipeCommandOutput extends CommandOutput {

    /**
     * Sets the stream that is filled with the piped to the output of the
     * command.
     *
     * @param stream
     *            the {@link OutputStream}.
     */
    void setPipe(OutputStream stream);

    /**
     * Sets the buffer size.
     *
     * @param size
     *            the size in bytes.
     */
    void setBufferSize(int size);

}
