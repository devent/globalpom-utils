package com.anrisoftware.globalpom.exec.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandExecException;
import com.anrisoftware.globalpom.exec.api.CommandInput;
import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.anrisoftware.globalpom.exec.api.ProcessTask;
import com.anrisoftware.globalpom.exec.command.CommandLine;
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeCommandInputFactory;
import com.anrisoftware.globalpom.exec.pipeoutputs.PipeCommandOutputFactory;
import com.anrisoftware.globalpom.threads.api.Threads;
import com.google.inject.assistedinject.Assisted;

class DefaultProcessTask implements ProcessTask {

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
        }
        return this;
    }

    private void startProcess(ProcessBuilder builder) throws IOException,
            InterruptedException, CommandExecException {
        Process process = builder.start();
        createProcessStreams(process);
        this.process = process;
        this.ret = process.waitFor();
        if (exitCodes != null && !checkExitCodes(ret, exitCodes)) {
            throw log.invalidExitCode(this, ret, exitCodes, commandLine);
        }
    }

    private void createProcessStreams(Process process) {
        if (output == null) {
            this.outputStream = new ByteArrayOutputStream();
            this.output = commandOutputFactory.create(outputStream);
        }
        if (error == null) {
            this.errorStream = new ByteArrayOutputStream();
            this.error = commandErrorFactory.create(errorStream);
        }
        if (input == null) {
            this.input = commandInputFactory.create();
        }
        input.setOutput(process.getOutputStream());
        output.setInput(process.getInputStream());
        error.setInput(process.getErrorStream());
        threads.submit(input);
        threads.submit(output);
        threads.submit(error);
    }

    private boolean checkExitCodes(int ret, int[] exitCodes) {
        return ArrayUtils.contains(exitCodes, ret);
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
}
