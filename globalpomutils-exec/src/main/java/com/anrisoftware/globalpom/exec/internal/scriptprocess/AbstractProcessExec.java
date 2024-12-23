/*
 * Copyright 2014-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.scriptprocess;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.File;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import jakarta.inject.Inject;

import org.joda.time.Duration;
import org.slf4j.Logger;

import com.anrisoftware.globalpom.exec.external.core.CommandExec;
import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.ProcessTask;
import com.anrisoftware.globalpom.exec.external.logoutputs.DebugLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.logoutputs.ErrorLogCommandOutputFactory;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandExecFactory;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandLineFactory;
import com.anrisoftware.globalpom.exec.external.scriptprocess.ScriptExec;
import com.anrisoftware.globalpom.threads.external.core.Threads;

/**
 * Executes a script.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public abstract class AbstractProcessExec implements ScriptExec {

    /**
     * Default {@code errString} value, set to {@code false}.
     */
    public static final Boolean ERR_STRING_DEFAULT = false;

    /**
     * Default {@code outString} value, set to {@code false}.
     */
    public static final boolean OUT_STRING_DEFAULT = false;

    /**
     * Default {@code destroyOnTimeout} value, set to {@code true}.
     */
    public static final boolean DESTROY_ON_TIMEOUT_DEFAULT = true;

    /**
     * Default {@code exitCode} value, set to {@code 0}.
     */
    public static final int EXIT_CODE_DEFAULT = 0;

    /**
     * Default {@code timeout} duration, set to 60 seconds.
     */
    public static final Duration TIMEOUT_DEFAULT = Duration.standardSeconds(60);

    private static final String LOG_KEY = "log";

    private static final String WORK_DIR_ARG = "pwd";

    private static final String ERR_STRING_ARG = "errString";

    private static final String OUT_STRING_ARG = "outString";

    public static final String TIMEOUT_ARG_ARG = "timeout";

    public static final String DESTROY_ON_TIMEOUT_ARG = "destroyOnTimeout";

    public static final String EXIT_CODES_ARG = "exitCodes";

    public static final String EXIT_CODE_ARG = "exitCode";

    private final Map<String, Object> args;

    private final Threads threads;

    private final Integer exitCode;

    private final int[] exitCodes;

    private final Boolean destroyOnTimeout;

    private final Duration timeout;

    private final boolean outString;

    private final boolean errString;

    private final boolean checkExitCodes;

    private final File pwd;

    @Inject
    private AbstractProcessExecLogger log;

    @Inject
    private ScriptCommandExecFactory scriptExecFactory;

    @Inject
    private ScriptCommandLineFactory lineFactory;

    @Inject
    private CommandExecFactory execFactory;

    @Inject
    private DebugLogCommandOutputFactory outputFactory;

    @Inject
    private ErrorLogCommandOutputFactory errorFactory;

    private String scriptString;

    /**
     * Sets the threads pool and the arguments.
     *
     * @param threads the {@link Threads} threads pool.
     *
     * @param args    the {@link Map} arguments.
     */
    protected AbstractProcessExec(Threads threads, Map<String, Object> args) {
        this.args = args;
        this.threads = threads;
        this.exitCode = getArg(EXIT_CODE_ARG, args, EXIT_CODE_DEFAULT);
        this.exitCodes = getArg(EXIT_CODES_ARG, args);
        this.checkExitCodes = getArg("checkExitCodes", args, true);
        this.destroyOnTimeout = getArg(DESTROY_ON_TIMEOUT_ARG, args, DESTROY_ON_TIMEOUT_DEFAULT);
        this.timeout = getArg(TIMEOUT_ARG_ARG, args, TIMEOUT_DEFAULT);
        this.outString = getArg(OUT_STRING_ARG, args, OUT_STRING_DEFAULT);
        this.errString = getArg(ERR_STRING_ARG, args, ERR_STRING_DEFAULT);
        this.pwd = getFileArg(WORK_DIR_ARG, args, System.getProperty("user.dir"));
    }

    @SuppressWarnings("unchecked")
    private <T> T getArg(String name, Map<String, Object> args) {
        return (T) (args.containsKey(name) ? args.get(name) : null);
    }

    @SuppressWarnings("unchecked")
    private <T> T getArg(String name, Map<String, Object> args, T defaultValue) {
        return (T) (args.containsKey(name) ? args.get(name) : defaultValue);
    }

    private File getFileArg(String name, Map<String, Object> args, String defaultValue) {
        File value = new File(defaultValue);
        Object f = getArg(name, args, value);
        if (f instanceof File) {
            return (File) f;
        } else if (f != null) {
            return new File(f.toString());
        } else {
            return value;
        }
    }

    @Override
    public ProcessTask call() throws CommandExecException {
        log.checkArgs(this, args);
        final CommandLine line = createCommandLine(lineFactory);
        line.setWorkingDir(pwd);
        CommandExec script = createExec();
        script.setObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                logExecutedScript(line);
            }

        });
        ProcessTask task = exec(line, script);
        return task;
    }

    @Override
    public String getScriptString() {
        return scriptString;
    }

    /**
     * Creates the command line for the process.
     *
     * @param commandLineFactory the {@link ScriptCommandLineFactory}.
     *
     * @return the {@link CommandLine}.
     */
    protected abstract CommandLine createCommandLine(ScriptCommandLineFactory commandLineFactory);

    private CommandExec createExec() {
        CommandExec script = scriptExecFactory.create(execFactory);
        script.setThreads(threads);
        if (exitCode != null && checkExitCodes) {
            script.setExitCode(exitCode);
        }
        if (exitCodes != null && checkExitCodes) {
            script.setExitCode(exitCodes);
        }
        if (destroyOnTimeout != null) {
            script.setDestroyOnTimeout(destroyOnTimeout);
        }
        return script;
    }

    private ProcessTask exec(CommandLine line, CommandExec script) throws CommandExecException {
        setupCommandError(script, line);
        setupCommandOutput(script, line);
        Future<ProcessTask> future = script.exec(line);
        try {
            if (timeout != null) {
                return future.get(timeout.getMillis(), MILLISECONDS);
            } else {
                return future.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ScriptExecException(e, line, script);
        } catch (ExecutionException e) {
            throw new ScriptExecException(e, line, script);
        } catch (TimeoutException e) {
            throw new ScriptExecException(e, line, script);
        }
    }

    protected void setupCommandOutput(CommandExec script, CommandLine line) {
        if (outString) {
            return;
        }
        Logger logger = (Logger) args.get(LOG_KEY);
        script.setCommandOutput(outputFactory.create(logger, line));
    }

    protected void setupCommandError(CommandExec script, CommandLine line) {
        if (errString) {
            return;
        }
        Logger logger = (Logger) args.get(LOG_KEY);
        script.setCommandError(errorFactory.create(logger, line));
    }

    private void logExecutedScript(CommandLine line) {
        String string = line.getExecutable().toString();
        log.executedScript(this, string);
        this.scriptString = string;
    }
}
