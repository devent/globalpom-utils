package com.anrisoftware.globalpom.exec.external.pipeoutputs;

import java.io.InputStream;

import com.anrisoftware.globalpom.exec.external.core.CommandInput;

/**
 * Pipes the read data from one stream to the standard input of the command.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
public interface PipeCommandInput extends CommandInput {

    /**
     * Sets the stream that is read and piped to the standard input of the
     * command.
     *
     * @param stream
     *            the {@link InputStream}.
     */
    void setPipe(InputStream stream);

    /**
     * Sets the buffer size.
     *
     * @param size
     *            the size in bytes.
     */
    void setBufferSize(int size);

}
