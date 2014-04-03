package com.anrisoftware.globalpom.exec.api;

import java.io.OutputStream;
import java.util.concurrent.Callable;

/**
 * Reads the standard output of the executed command.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface CommandInput extends Callable<CommandInput> {

    /**
     * Sets the stream that is connected to the standard input of the executed
     * command.
     * 
     * @param stream
     *            the {@link OutputStream}.
     */
    void setOutput(OutputStream stream);

    /**
     * Clones the standard input writer. The cloned writer have the same
     * connected output stream.
     * 
     * @return the cloned {@link CommandInput}.
     */
    CommandInput clone();

    /**
     * Writes to the standard input to the connected stream.
     */
    @Override
    CommandInput call() throws Exception;
}
