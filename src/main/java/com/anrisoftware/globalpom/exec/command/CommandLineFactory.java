package com.anrisoftware.globalpom.exec.command;

import java.io.File;

/**
 * Factory to create a command line.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface CommandLineFactory {

    /**
     * Create a command line from an executable.
     * 
     * @param executable
     *            the {@link String} executable.
     * 
     * @return the {@link CommandLine}.
     */
    CommandLine create(String executable);

    /**
     * Create a command line from an executable.
     * 
     * @param executable
     *            the {@link File} executable.
     * 
     * @return the {@link CommandLine}.
     */
    CommandLine create(File executable);

}
