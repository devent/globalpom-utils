package com.anrisoftware.globalpom.exec.pipeoutputs;

import java.io.OutputStream;

/**
 * Factory to create the pipe output.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface PipeCommandOutputFactory {

    /**
     * Creates the pipe output that writes from the output of the command to the
     * specified stream.
     * 
     * @param stream
     *            the {@link OutputStream}.
     * 
     * @return the {@link PipeCommandOutput}.
     */
    PipeCommandOutput create(OutputStream output);

}
