/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.core;

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

import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.globalpom.exec.external.core.CommandInput;
import com.anrisoftware.globalpom.exec.external.core.CommandInterruptedException;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.CommandOutput;
import com.anrisoftware.globalpom.exec.external.core.InvalidExitCodeException;
import com.anrisoftware.globalpom.exec.external.core.ProcessTask;
import com.anrisoftware.globalpom.exec.external.core.StartCommandException;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandInputFactory;
import com.anrisoftware.globalpom.exec.external.pipeoutputs.PipeCommandOutputFactory;
import com.anrisoftware.globalpom.threads.external.core.Threads;
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

    private boolean destroyOnInterrupted;

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
    public void setDestroyOnInterrupted(boolean flag) {
        this.destroyOnInterrupted = flag;
    }

    @Override
    public boolean isDestroyOnInterrupted() {
        return destroyOnInterrupted;
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
            throw new StartCommandException(this, e, commandLine);
        } catch (InterruptedException e) {
            stopProcessIfInterrupted();
            Thread.currentThread().interrupt();
            throw new CommandInterruptedException(this, e, commandLine);
        } catch (ExecutionException e) {
            throw new StartCommandException(this, e.getCause(), commandLine);
        }
        return this;
    }

    private void stopProcessIfInterrupted() {
        if (!isDestroyOnInterrupted()) {
            return;
        }
        destroy();
    }

    private void startProcess(ProcessBuilder builder)
            throws IOException, InterruptedException, CommandExecException, ExecutionException {
        Process process = builder.start();
        List<Future<?>> streamsTasks = createProcessStreams(process);
        this.process = process;
        this.ret = process.waitFor();
        waitForStreams(streamsTasks);
        setChanged();
        notifyObservers();
        if (exitCodes != null && !checkExitCodes(ret, exitCodes)) {
            throw new InvalidExitCodeException(this, ret, exitCodes, commandLine);
        }
    }

    private void waitForStreams(List<Future<?>> streamsTasks) throws InterruptedException, ExecutionException {
        for (Future<?> future : streamsTasks) {
            future.get();
        }
    }

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
        return new ArrayList<>(asList(threads.submit(input), threads.submit(output), threads.submit(error)));
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
        return new ToStringBuilder(this).append(EXECUTABLE, commandLine.getExecutable()).toString();
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
