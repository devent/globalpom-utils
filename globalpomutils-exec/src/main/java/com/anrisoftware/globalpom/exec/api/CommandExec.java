/*
 * Copyright 2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-exec.
 *
 * globalpomutils-exec is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-exec is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-exec. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.api;

import java.beans.PropertyChangeListener;
import java.util.concurrent.Future;

import com.anrisoftware.globalpom.exec.command.DefaultCommandLine;
import com.anrisoftware.globalpom.threads.api.Threads;

/**
 * Executes an external command.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface CommandExec {

    /**
     * Sets the threads pool.
     * 
     * @param threads
     *            the {@link Threads}.
     */
    void setThreads(Threads threads);

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
     *            the {@link DefaultCommandLine}.
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
