package com.anrisoftware.globalpom.exec.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandExec;
import com.anrisoftware.globalpom.exec.api.CommandExecException;
import com.anrisoftware.globalpom.exec.api.CommandInput;
import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.anrisoftware.globalpom.exec.api.ProcessTask;
import com.anrisoftware.globalpom.exec.command.CommandLine;
import com.anrisoftware.globalpom.threads.api.ListenableFuture.Status;
import com.anrisoftware.globalpom.threads.api.Threads;

/**
 * Executes an external command.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class DefaultCommandExec implements CommandExec {

    @Inject
    private DefaultCommandExecLogger log;

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
    public Future<ProcessTask> exec(CommandLine commandLine,
            PropertyChangeListener... listeners) throws CommandExecException {
        try {
            ProcessTask task = createProcessTask(commandLine);
            return threads.submit(task, setupTimeoutListener(task, listeners));
        } catch (IOException e) {
            throw log.errorExecuteCommand(this, e, commandLine);
        }
    }

    private PropertyChangeListener[] setupTimeoutListener(ProcessTask task,
            PropertyChangeListener... listeners) {
        return destroyOnTimeout ? addTimeoutListener(task, listeners)
                : listeners;
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

    private ProcessTask createProcessTask(CommandLine commandLine)
            throws IOException {
        DefaultProcessTask task = processTaskFactory.create(commandLine);
        task.setThreads(threads);
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

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
