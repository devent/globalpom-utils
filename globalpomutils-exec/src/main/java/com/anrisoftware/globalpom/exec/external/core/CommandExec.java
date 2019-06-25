/**
 * Copyright © 2014 Erwin Müller (erwin.mueller@anrisoftware.com)
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

import java.beans.PropertyChangeListener;
import java.util.Observer;
import java.util.concurrent.Future;

import com.anrisoftware.globalpom.exec.internal.command.DefaultCommandLine;
import com.anrisoftware.globalpom.threads.external.core.Threads;

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
     * @param threads the {@link Threads}.
     */
    void setThreads(Threads threads);

    /**
     * Sets that reads the standard output of the command.
     *
     * @param output the {@link CommandOutput}.
     */
    void setCommandOutput(CommandOutput output);

    /**
     * Sets that reads the error output of the command.
     *
     * @param error the {@link CommandOutput}.
     */
    void setCommandError(CommandOutput error);

    /**
     * Sets that writes to the standard input of the command.
     *
     * @param input the {@link CommandInput}.
     */
    void setCommandInput(CommandInput input);

    /**
     * Sets the valid command code(s).
     *
     * @param code the valid command code(s).
     */
    void setExitCode(int... code);

    /**
     * Sets to destroy the executed command on timeout.
     *
     * @param flag sets to {@code true} to destroy the executed command on timeout.
     */
    void setDestroyOnTimeout(boolean flag);

    /**
     * Sets the additional observer(s) for the command task.
     *
     * @param observer the {@link Observer} observer(s).
     */
    void setObserver(Observer... observer);

    /**
     * Executes the command specified by the command line.
     *
     * @param commandLine the {@link DefaultCommandLine}.
     *
     * @param listeners   the {@link PropertyChangeListener} listeners that are
     *                    informed when the command finishes.
     *
     * @return the {@link Future} of {@link ProcessTask}.
     *
     * @throws CommandExecException if there was an error executing the command; if
     *                              the command was not found; if the exit code of
     *                              the command is not valid.
     */
    Future<ProcessTask> exec(CommandLine commandLine, PropertyChangeListener... listeners) throws CommandExecException;

}
