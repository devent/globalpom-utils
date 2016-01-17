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
package com.anrisoftware.globalpom.exec.core;

import static java.util.Arrays.asList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandExecException;
import com.anrisoftware.globalpom.exec.api.CommandInput;
import com.anrisoftware.globalpom.exec.api.CommandLine;
import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.anrisoftware.globalpom.exec.api.ProcessTask;
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeCommandInputFactory;
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeCommandOutputFactory;
import com.anrisoftware.globalpom.threads.api.Threads;
import com.google.inject.assistedinject.Assisted;

/**
 * Executes command task.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
class DefaultProcessTask extends Observable implements ProcessTask {

    private static final String EXECUTABLE = "executable";

    private final CommandLine commandLine;

    @Inject
    private DefaultProcessTaskLogger log;

    @Inject
    private PipeCommandOutputFactory commandOutputFactory;

    @Inject
    private PipeCommandOutputFactory commandErrorFactory;

    @Inject
    private PipeCommandInputFactory commandInputFactory;

    private Process process;

    private CommandOutput output;

    private CommandOutput error;

    private CommandInput input;

    private int[] exitCodes;

    private Threads threads;

    private int ret;

    private ByteArrayOutputStream outputStream;

    private ByteArrayOutputStream errorStream;

    @Inject
    DefaultProcessTask(@Assisted CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public CommandLine getCommandLine() {
        return commandLine;
    }

    public void setThreads(Threads threads) {
        this.threads = threads;
    }

    public void setCommandOutput(CommandOutput output) {
        this.output = output;
    }

    public void setCommandError(CommandOutput error) {
        this.error = error;
    }

    public void setCommandInput(CommandInput input) {
        this.input = input;
    }

    public void setExitCodes(int[] exitCodes) {
        if (exitCodes != null) {
            this.exitCodes = exitCodes.clone();
        }
    }

    @Override
    public ProcessTask call() throws CommandExecException {
        List<String> command = commandLine.getCommand();
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(commandLine.getWorkingDir());
        builder.redirectOutput(Redirect.PIPE);
        builder.redirectError(Redirect.PIPE);
        builder.redirectInput(Redirect.PIPE);
        try {
            startProcess(builder);
        } catch (IOException e) {
            throw log.errorStartCommand(this, e, commandLine);
        } catch (InterruptedException e) {
            throw log.commandInterrupted(this, e, commandLine);
        } catch (ExecutionException e) {
            throw log.errorStartCommand(this, e.getCause(), commandLine);
        }
        return this;
    }

    private void startProcess(ProcessBuilder builder) throws IOException,
            InterruptedException, CommandExecException, ExecutionException {
        Process process = builder.start();
        List<Future<?>> streamsTasks = createProcessStreams(process);
        this.process = process;
        this.ret = process.waitFor();
        waitForStreams(streamsTasks);
        setChanged();
        notifyObservers();
        if (exitCodes != null && !checkExitCodes(ret, exitCodes)) {
            throw log.invalidExitCode(this, ret, exitCodes, commandLine);
        }
    }

    private void waitForStreams(List<Future<?>> streamsTasks)
            throws InterruptedException, ExecutionException {
        for (Future<?> future : streamsTasks) {
            future.get();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Future<?>> createProcessStreams(Process process) {
        if (output == null) {
            this.outputStream = new ByteArrayOutputStream();
            this.output = commandOutputFactory.create(outputStream);
        }
        if (error == null) {
            this.errorStream = new ByteArrayOutputStream();
            this.error = commandErrorFactory.create(errorStream);
        }
        if (input == null) {
            byte[] bytes = "".getBytes();
            InputStream stream = new ByteArrayInputStream(bytes);
            this.input = commandInputFactory.create(stream);
        }
        input.setOutput(process.getOutputStream());
        output.setInput(process.getInputStream());
        error.setInput(process.getErrorStream());
        return new ArrayList<Future<?>>(asList(threads.submit(input),
                threads.submit(output), threads.submit(error)));
    }

    private boolean checkExitCodes(int ret, int[] exitCodes) {
        return ArrayUtils.contains(exitCodes, ret);
    }

    @Override
    public Process getProcess() {
        return process;
    }

    @Override
    public void destroy() {
        process.destroy();
    }

    @Override
    public String getOut() {
        if (outputStream != null) {
            return outputStream.toString();
        } else {
            return null;
        }
    }

    @Override
    public String getErr() {
        if (errorStream != null) {
            return errorStream.toString();
        } else {
            return null;
        }
    }

    @Override
    public int getExitValue() {
        return ret;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(EXECUTABLE,
                commandLine.getExecutable()).toString();
    }

    @Override
    public void addObserver(Observer... observer) {
        for (Observer o : observer) {
            addObserver(o);
        }
    }

    @Override
    public void removeObserver(Observer... observer) {
        for (Observer o : observer) {
            deleteObserver(o);
        }
    }
}
