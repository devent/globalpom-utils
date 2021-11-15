/*
 * Copyright 2014-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.logoutputs;

import static com.anrisoftware.globalpom.exec.internal.logoutputs.LogOutputsModule.getDebugLogCommandOutputFactory;
import static com.anrisoftware.globalpom.exec.internal.logoutputs.LogOutputsModule.getErrorLogCommandOutputFactory;
import static com.anrisoftware.globalpom.exec.internal.logoutputs.LogOutputsModule.getInfoLogCommandOutputFactory;
import static com.anrisoftware.globalpom.exec.internal.logoutputs.LogOutputsModule.getTraceLogCommandOutputFactory;
import static org.apache.commons.io.FilenameUtils.getName;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;

import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.CommandOutput;
import com.anrisoftware.globalpom.exec.external.logoutputs.DebugLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.ErrorLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.InfoLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.LogCommandOutput;
import com.anrisoftware.globalpom.exec.external.logoutputs.TraceLogCommandOutputFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * Pipes the read data from the output of the command to a logger.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public abstract class AbstractLogCommandOutput implements LogCommandOutput {

    /**
     *
     * @param logger      the {@link Logger}
     * @param commandLine the {@link CommandLine}
     * @return the {@link CommandOutput}
     */
    public static CommandOutput createInfoLogCommandOutput(Logger logger, CommandLine commandLine) {
        return getInfoLogCommandOutputFactory().create(logger, commandLine);
    }

    /**
     *
     * @param logger      the {@link Logger}
     * @param commandLine the {@link CommandLine}
     * @return the {@link CommandOutput}
     */
    public static CommandOutput createDebugLogCommandOutput(Logger logger, CommandLine commandLine) {
        return getDebugLogCommandOutputFactory().create(logger, commandLine);
    }

    /**
     *
     * @param logger      the {@link Logger}
     * @param commandLine the {@link CommandLine}
     * @return the {@link CommandOutput}
     */
    public static CommandOutput createTraceLogCommandOutput(Logger logger, CommandLine commandLine) {
        return getTraceLogCommandOutputFactory().create(logger, commandLine);
    }

    /**
     *
     * @param logger      the {@link Logger}
     * @param commandLine the {@link CommandLine}
     * @return the {@link CommandOutput}
     */
    public static CommandOutput createErrorLogCommandOutput(Logger logger, CommandLine commandLine) {
        return getErrorLogCommandOutputFactory().create(logger, commandLine);
    }

    /**
     * Logs the output as info level.
     *
     * @author Erwin Mueller, erwin.mueller@deventm.org
     * @since 2.0
     */
    public static class InfoLogCommandOutput extends AbstractLogCommandOutput {

        @Inject
        private InfoLogCommandOutputFactory outputFactory;

        @Inject
        InfoLogCommandOutput(@Assisted Logger logger, @Assisted CommandLine commandLine) {
            super(logger, commandLine);
        }

        @Override
        public CommandOutput clone() {
            CommandOutput commandOutput = outputFactory.create(getLogger(), getCommandLine());
            commandOutput.setInput(getInput());
            return commandOutput;
        }

        @Override
        protected void logLine(String line) {
            getLogger().info(getMessage(), getCommand(), line);
        }
    }

    /**
     * Logs the output as debug level.
     *
     * @author Erwin Mueller, erwin.mueller@deventm.org
     * @since 2.0
     */
    public static class DebugLogCommandOutput extends AbstractLogCommandOutput {

        @Inject
        private DebugLogCommandOutputFactory outputFactory;

        @Inject
        DebugLogCommandOutput(@Assisted Logger logger, @Assisted CommandLine commandLine) {
            super(logger, commandLine);
        }

        @Override
        public CommandOutput clone() {
            CommandOutput commandOutput = outputFactory.create(getLogger(), getCommandLine());
            commandOutput.setInput(getInput());
            return commandOutput;
        }

        @Override
        protected void logLine(String line) {
            getLogger().debug(getMessage(), getCommand(), line);
        }
    }

    /**
     * Logs the output as trace level.
     *
     * @author Erwin Mueller, erwin.mueller@deventm.org
     * @since 2.0
     */
    public static class TraceLogCommandOutput extends AbstractLogCommandOutput {

        @Inject
        private TraceLogCommandOutputFactory outputFactory;

        @Inject
        TraceLogCommandOutput(@Assisted Logger logger, @Assisted CommandLine commandLine) {
            super(logger, commandLine);
        }

        @Override
        public CommandOutput clone() {
            CommandOutput commandOutput = outputFactory.create(getLogger(), getCommandLine());
            commandOutput.setInput(getInput());
            return commandOutput;
        }

        @Override
        protected void logLine(String line) {
            getLogger().trace(getMessage(), getCommand(), line);
        }
    }

    /**
     * Logs the output as error level.
     *
     * @author Erwin Mueller, erwin.mueller@deventm.org
     * @since 2.0
     */
    public static class ErrorLogCommandOutput extends AbstractLogCommandOutput {

        @Inject
        private ErrorLogCommandOutputFactory outputFactory;

        @Inject
        ErrorLogCommandOutput(@Assisted Logger logger, @Assisted CommandLine commandLine) {
            super(logger, commandLine);
        }

        @Override
        public CommandOutput clone() {
            CommandOutput commandOutput = outputFactory.create(getLogger(), getCommandLine());
            commandOutput.setInput(getInput());
            return commandOutput;
        }

        @Override
        protected void logLine(String line) {
            getLogger().error(getMessage(), getCommand(), line);
        }
    }

    private final CommandLine commandLine;

    private final String command;

    private InputStream stream;

    private Logger logger;

    private int bufferSize;

    private String message;

    /**
     * @param logger      the {@link Logger}.
     *
     * @param commandLine the the {@link CommandLine} for the logging message.
     */
    protected AbstractLogCommandOutput(Logger logger, CommandLine commandLine) {
        this.logger = logger;
        this.message = "{}: '{}'";
        this.commandLine = commandLine;
        this.command = getName(commandLine.getExecutable().toString());
        this.bufferSize = 8192;
    }

    @Override
    public void setInput(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public InputStream getInput() {
        return stream;
    }

    @Override
    public void setBufferSize(int size) {
        this.bufferSize = size;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public CommandLine getCommandLine() {
        return commandLine;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public CommandOutput call() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream), bufferSize);
        for (String s = reader.readLine(); s != null; s = reader.readLine()) {
            logLine(s);
        }
        return this;
    }

    @Override
    public abstract CommandOutput clone();

    /**
     * Log the line red from the command output.
     *
     * @param line the {@link String} line.
     */
    protected abstract void logLine(String line);

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
