package com.anrisoftware.globalpom.exec.core;

import com.anrisoftware.globalpom.exec.command.CommandLine;

/**
 * Factory to create the default process task.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
interface DefaultProcessTaskFactory {

    /**
     * Creates the default process task.
     * 
     * @param commandLine
     *            the {@link CommandLine}.
     * 
     * @return the {@link DefaultProcessTask}.
     */
    DefaultProcessTask create(CommandLine commandLine);
}
