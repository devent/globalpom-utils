package com.anrisoftware.globalpom.exec.external.logoutputs;

import java.io.InputStream;

import org.slf4j.Logger;

import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.CommandOutput;

/**
 * Pipes the read data from the output of the command to a logger.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
public interface LogCommandOutput extends CommandOutput {

    /**
     * Returns the stream that is connected to output of the executed command.
     *
     * @return the {@link InputStream}.
     */
    InputStream getInput();

    /**
     * Sets the buffer size.
     *
     * @param size
     *            the size in bytes.
     */
    void setBufferSize(int size);

    /**
     * Sets the logger that is filled with the piped to the output of the
     * command.
     *
     * @param logger
     *            the {@link Logger}.
     */
    void setLogger(Logger logger);

    /**
     * Returns the logger that is filled with the piped to the output of the
     * command.
     *
     * @return the {@link Logger}.
     */
    Logger getLogger();

    /**
     * Returns the command line for the logging message.
     *
     * @return the {@link CommandLine}.
     */
    CommandLine getCommandLine();

    /**
     * Returns the command for the logging message.
     *
     * @return the {@link String} command.
     */
    String getCommand();

    /**
     * Sets the message string.
     *
     * @param message
     *            the {@link String} message.
     */
    void setMessage(String message);

    /**
     * Returns the message string.
     *
     * @return the {@link String} message.
     */
    String getMessage();

}
