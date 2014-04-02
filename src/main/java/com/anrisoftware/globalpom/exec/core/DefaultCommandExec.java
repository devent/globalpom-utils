package com.anrisoftware.globalpom.exec.core;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.concurrent.Future;

import javax.inject.Inject;

import com.anrisoftware.globalpom.exec.api.CommandExec;
import com.anrisoftware.globalpom.exec.api.CommandInput;
import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.anrisoftware.globalpom.exec.api.ProcessTask;
import com.anrisoftware.globalpom.exec.command.CommandLine;
import com.anrisoftware.globalpom.threads.api.Threads;

public class DefaultCommandExec implements CommandExec {

    @Inject
    private DefaultProcessTaskFactory processTaskFactory;

    private Threads threads;

    private CommandOutput output;

    private CommandOutput error;

    private CommandInput input;

    private int[] exitCodes;

    public DefaultCommandExec() {
        this.exitCodes = null;
    }

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
    public Future<ProcessTask> exec(CommandLine commandLine,
            PropertyChangeListener... listeners) {
        try {
            ProcessTask task = createProcessTask(commandLine);
            return threads.submit(task, listeners);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub
        return null;
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
}
