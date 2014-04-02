package com.anrisoftware.globalpom.exec.pipeoutputs;

import java.io.InputStream;

/**
 * Factory to create the pipe input.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface PipeCommandInputFactory {

    /**
     * Creates the pipe input that reads the specified stream to the standard
     * input of the command.
     * 
     * @param stream
     *            the {@link InputStream}.
     * 
     * @return the {@link PipeCommandInput}.
     */
    PipeCommandInput create(InputStream stream);

}
