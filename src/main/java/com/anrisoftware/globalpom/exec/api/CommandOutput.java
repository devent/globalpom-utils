package com.anrisoftware.globalpom.exec.api;

import java.io.InputStream;
import java.util.concurrent.Callable;

/**
 * Reads the standard output of the executed command.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface CommandOutput extends Callable<CommandOutput> {

    /**
     * Sets the stream that is connected to output of the executed command.
     * 
     * @param stream
     *            the {@link InputStream}.
     */
    void setInput(InputStream stream);

    /**
     * Clones the standard output reader. The cloned reader have the same
     * connected input stream.
     * 
     * @return the cloned {@link CommandOutput}.
     */
    CommandOutput clone();

    /**
     * Reads the standard output from the connected stream.
     */
    @Override
    CommandOutput call() throws Exception;
}
