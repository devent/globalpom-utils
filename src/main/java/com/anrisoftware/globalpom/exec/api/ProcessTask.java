/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.exec.api;

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
}
