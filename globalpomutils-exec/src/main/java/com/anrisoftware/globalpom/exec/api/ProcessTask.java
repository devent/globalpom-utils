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

import java.util.Observer;
import java.util.concurrent.Callable;

/**
 * Executes command task.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface ProcessTask extends Callable<ProcessTask> {

    /**
     * Executes the command.
     */
    @Override
    ProcessTask call() throws CommandExecException;

    /**
     * Returns the command line of the process.
     * 
     * @return the {@link CommandLine}.
     */
    CommandLine getCommandLine();

    /**
     * Returns the process of the executed command.
     * 
     * @return the {@link Process}.
     */
    Process getProcess();

    /**
     * Kills the process.
     */
    void destroy();

    /**
     * Returns the standard output of the process after the process finished.
     * 
     * @return the {@link String} standard output.
     */
    String getOut();

    /**
     * Returns the standard error output of the process after the process
     * finished.
     * 
     * @return the {@link String} standard error output.
     */
    String getErr();

    /**
     * Returns the exit value of the process after the process finished.
     * 
     * @return the exit value.
     */
    int getExitValue();

    /**
     * Adds the process task observer(s).
     * 
     * @param observer
     *            the {@link Observer}.
     */
    void addObserver(Observer... observer);

    /**
     * Removes the process task observer(s).
     * 
     * @param observer
     *            the {@link Observer}.
     */
    void removeObserver(Observer... observer);

}
