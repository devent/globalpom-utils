/*
 * Copyright 2014-2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.internal.core;

import static com.anrisoftware.globalpom.exec.internal.core.DefaultProcessModule.getCommandExecFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.external.core.CommandExec;
import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory;
import com.anrisoftware.globalpom.exec.external.core.CommandInput;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.CommandOutput;
import com.anrisoftware.globalpom.exec.external.core.ProcessTask;
import com.anrisoftware.globalpom.threads.external.core.Threads;
import com.anrisoftware.globalpom.threads.external.core.ListenableFuture.Status;

/**
 * Executes an external command.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class DefaultCommandExec implements CommandExec {

    /**
     * @see CommandExecFactory#create()
     */
    public static CommandExec createCommandExec() {
        return getCommandExecFactory().create();
    }

    private final List<Observer> observers;

    @Inject
    private DefaultProcessTaskFactory processTaskFactory;

    private Threads threads;

    private CommandOutput output;

    private CommandOutput error;

    private CommandInput input;

    private int[] exitCodes;

    private boolean destroyOnTimeout;

    DefaultCommandExec() {
        this.exitCodes = null;
        this.destroyOnTimeout = true;
        this.observers = new ArrayList<Observer>();
    }

    @Override
    public void setThreads(Threads threads) {
        this.threads = threads;
    }

    @Override
    public void setCommandOutput(CommandOutput output) {
        this.output = output;
    }

    @Override
    public void setCommandError(CommandOutput error) {
        this.error = error;
    }

    @Override
    public void setCommandInput(CommandInput input) {
        this.input = input;
    }

    @Override
    public void setExitCode(int... codes) {
        this.exitCodes = codes;
    }

    @Override
    public void setDestroyOnTimeout(boolean flag) {
        this.destroyOnTimeout = flag;
    }

    @Override
    public void setObserver(Observer... observer) {
        this.observers.addAll(Arrays.asList(observer));
    }

    @Override
    public Future<ProcessTask> exec(CommandLine commandLine,
            PropertyChangeListener... listeners) throws CommandExecException {
        try {
            ProcessTask task = createProcessTask(commandLine, observers);
            return threads.submit(task, setupListener(task, listeners));
        } catch (IOException e) {
            throw new ExecuteCommandException(this, e, commandLine);
        }
    }

    private ProcessTask createProcessTask(CommandLine commandLine,
            List<Observer> observers) throws IOException {
        DefaultProcessTask task = processTaskFactory.create(commandLine);
        task.setThreads(threads);
        task.addObserver(observers.toArray(new Observer[] {}));
        if (output != null) {
            task.setCommandOutput(output.clone());
        }
        if (error != null) {
            task.setCommandError(error.clone());
        }
        if (input != null) {
            task.setCommandInput(input.clone());
        }
        task.setExitCodes(exitCodes);
        return task;
    }

    private PropertyChangeListener[] setupListener(ProcessTask task,
            PropertyChangeListener... l) {
        return destroyOnTimeout ? addTimeoutListener(task, l) : l;
    }

    private PropertyChangeListener[] addTimeoutListener(final ProcessTask task,
            PropertyChangeListener... listeners) {
        return ArrayUtils.add(listeners, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Status status = (Status) evt.getNewValue();
                if (status == Status.TIMEOUT) {
                    task.destroy();
                }
            }
        });
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
