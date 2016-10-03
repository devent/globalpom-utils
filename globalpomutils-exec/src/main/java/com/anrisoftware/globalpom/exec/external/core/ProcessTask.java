/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.exec.external.core;

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
