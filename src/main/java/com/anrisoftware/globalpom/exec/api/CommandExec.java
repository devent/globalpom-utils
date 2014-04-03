package com.anrisoftware.globalpom.exec.api;

import java.beans.PropertyChangeListener;
import java.util.concurrent.Future;

import com.anrisoftware.globalpom.exec.command.CommandLine;

/**
 * Executes an external command.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface CommandExec {

    /**
     * Sets that reads the standard output of the command.
     * 
     * @param output
     *            the {@link CommandOutput}.
     */
    void setCommandOutput(CommandOutput output);

    /**
     * Sets that reads the error output of the command.
     * 
     * @param output
     *            the {@link CommandOutput}.
     */
    void setCommandError(CommandOutput error);

    /**
     * Sets that writes to the standard input of the command.
     * 
     * @param input
     *            the {@link CommandInput}.
     */
    void setCommandInput(CommandInput input);

    /**
     * Sets the valid command code(s).
     * 
     * @param code
     *            the valid command code(s).
     */
    void setExitCode(int... code);

    /**
     * Sets to destroy the executed command on timeout.
     * 
     * @param flag
     *            sets to {@code true} to destroy the executed command on
     *            timeout.
     */
    void setDestroyOnTimeout(boolean flag);

    /**
     * Executes the command specified by the command line.
     * 
     * @param commandLine
     *            the {@link CommandLine}.
     * 
     * @param listeners
     *            the {@link PropertyChangeListener} listeners that are informed
     *            when the command finishes.
     * 
     * @return the {@link Future} of {@link ProcessTask}.
     * 
     * @throws CommandExecException
     *             if there was an error executing the command; if the command
     *             was not found; if the exit code of the command is not valid.
     */
    Future<ProcessTask> exec(CommandLine commandLine,
            PropertyChangeListener... listeners) throws CommandExecException;

}
