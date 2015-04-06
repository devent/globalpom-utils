/*
 * Copyright 2014-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.logoutputs;

import static com.anrisoftware.globalpom.exec.logoutputs.LogOutputsModule.getDebugLogCommandOutputFactory;
import static com.anrisoftware.globalpom.exec.logoutputs.LogOutputsModule.getErrorLogCommandOutputFactory;
import static com.anrisoftware.globalpom.exec.logoutputs.LogOutputsModule.getInfoLogCommandOutputFactory;
import static com.anrisoftware.globalpom.exec.logoutputs.LogOutputsModule.getTraceLogCommandOutputFactory;
import static org.apache.commons.io.FilenameUtils.getName;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;

import com.anrisoftware.globalpom.exec.api.CommandLine;
import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.google.inject.assistedinject.Assisted;

/**
 * Pipes the read data from the output of the command to a logger.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public abstract class AbstractLogCommandOutput implements CommandOutput {

    /**
     * @see InfoLogCommandOutputFactory#create(Logger, CommandLine)
     */
    public static InfoLogCommandOutput createInfoLogCommandOutput(
            Logger logger, CommandLine commandLine) {
        return getInfoLogCommandOutputFactory().create(logger, commandLine);
    }

    /**
     * @see DebugLogCommandOutputFactory#create(Logger, CommandLine)
     */
    public static DebugLogCommandOutput createDebugLogCommandOutput(
            Logger logger, CommandLine commandLine) {
        return getDebugLogCommandOutputFactory().create(logger, commandLine);
    }

    /**
     * @see TraceLogCommandOutputFactory#create(Logger, CommandLine)
     */
    public static TraceLogCommandOutput createTraceLogCommandOutput(
            Logger logger, CommandLine commandLine) {
        return getTraceLogCommandOutputFactory().create(logger, commandLine);
    }

    /**
     * @see ErrorLogCommandOutputFactory#create(Logger, CommandLine)
     */
    public static ErrorLogCommandOutput createErrorLogCommandOutput(
            Logger logger, CommandLine commandLine) {
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
        InfoLogCommandOutput(@Assisted Logger logger,
                @Assisted CommandLine commandLine) {
            super(logger, commandLine);
        }

        @Override
        public InfoLogCommandOutput clone() {
            InfoLogCommandOutput commandOutput = outputFactory.create(
                    getLogger(), getCommandLine());
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
        DebugLogCommandOutput(@Assisted Logger logger,
                @Assisted CommandLine commandLine) {
            super(logger, commandLine);
        }

        @Override
        public DebugLogCommandOutput clone() {
            DebugLogCommandOutput commandOutput = outputFactory.create(
                    getLogger(), getCommandLine());
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
        TraceLogCommandOutput(@Assisted Logger logger,
                @Assisted CommandLine commandLine) {
            super(logger, commandLine);
        }

        @Override
        public TraceLogCommandOutput clone() {
            TraceLogCommandOutput commandOutput = outputFactory.create(
                    getLogger(), getCommandLine());
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
        ErrorLogCommandOutput(@Assisted Logger logger,
                @Assisted CommandLine commandLine) {
            super(logger, commandLine);
        }

        @Override
        public ErrorLogCommandOutput clone() {
            ErrorLogCommandOutput commandOutput = outputFactory.create(
                    getLogger(), getCommandLine());
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
     * @param logger
     *            the {@link Logger}.
     * 
     * @param commandLine
     *            the the {@link CommandLine} for the logging message.
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

    /**
     * Returns the stream that is connected to output of the executed command.
     * 
     * @return the {@link InputStream}.
     */
    public InputStream getInput() {
        return stream;
    }

    /**
     * Sets the buffer size.
     * 
     * @param size
     *            the size in bytes.
     */
    public void setBufferSize(int size) {
        this.bufferSize = size;
    }

    /**
     * Sets the logger that is filled with the piped to the output of the
     * command.
     * 
     * @param logger
     *            the {@link Logger}.
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Returns the logger that is filled with the piped to the output of the
     * command.
     * 
     * @return the {@link Logger}.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns the command line for the logging message.
     * 
     * @return the {@link CommandLine}.
     */
    public CommandLine getCommandLine() {
        return commandLine;
    }

    /**
     * Returns the command for the logging message.
     * 
     * @return the {@link String} command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets the message string.
     * 
     * @param message
     *            the {@link String} message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the message string.
     * 
     * @return the {@link String} message.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public CommandOutput call() throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream), bufferSize);
        for (String s = reader.readLine(); s != null; s = reader.readLine()) {
            logLine(s);
        }
        return this;
    }

    @Override
    public abstract AbstractLogCommandOutput clone();

    /**
     * Log the line red from the command output.
     * 
     * @param line
     *            the {@link String} line.
     */
    protected abstract void logLine(String line);

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
