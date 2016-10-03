/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.internal.script;

import static com.anrisoftware.globalpom.exec.internal.script.ScriptCommandModule.getScriptCommandExecFactory;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.external.core.CommandExec;
import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.globalpom.exec.external.core.CommandExecFactory;
import com.anrisoftware.globalpom.exec.external.core.CommandInput;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;
import com.anrisoftware.globalpom.exec.external.core.CommandOutput;
import com.anrisoftware.globalpom.exec.external.core.ProcessTask;
import com.anrisoftware.globalpom.exec.external.script.ScriptCommandExecFactory;
import com.anrisoftware.globalpom.threads.external.core.Threads;
import com.google.inject.assistedinject.Assisted;

/**
 * Executes a script created from a loaded template.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public class ScriptCommandExec implements CommandExec {

    private static final String EXEC = "exec";

    /**
     * @see ScriptCommandExecFactory#create(CommandExecFactory)
     */
    public static CommandExec createScriptCommandExec(
            CommandExecFactory execFactory) {
        return getScriptCommandExecFactory().create(execFactory);
    }

    private final CommandExec exec;

    private final Observer scriptFileObserver;

    /**
     * @see ScriptCommandExecFactory#create(CommandExecFactory)
     */
    @Inject
    ScriptCommandExec(@Assisted CommandExecFactory execFactory) {
        this.exec = execFactory.create();
        this.scriptFileObserver = new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                ProcessTask task = (ProcessTask) o;
                deleteScriptFile(task);
            }
        };
        exec.setObserver(scriptFileObserver);
    }

    @Override
    public void setThreads(Threads threads) {
        exec.setThreads(threads);
    }

    @Override
    public void setCommandOutput(CommandOutput output) {
        exec.setCommandOutput(output);
    }

    @Override
    public void setCommandError(CommandOutput error) {
        exec.setCommandError(error);
    }

    @Override
    public void setCommandInput(CommandInput input) {
        exec.setCommandInput(input);
    }

    @Override
    public void setExitCode(int... codes) {
        exec.setExitCode(codes);
    }

    @Override
    public void setDestroyOnTimeout(boolean flag) {
        exec.setDestroyOnTimeout(flag);
    }

    @Override
    public void setObserver(Observer... observer) {
        exec.setObserver(observer);
    }

    @Override
    public Future<ProcessTask> exec(CommandLine commandLine,
            PropertyChangeListener... listeners) throws CommandExecException {
        return exec.exec(commandLine, listeners);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(EXEC, exec).toString();
    }

    private void deleteScriptFile(ProcessTask task) {
        ScriptCommandLine line = (ScriptCommandLine) task.getCommandLine();
        ((File) line.getExecutable()).delete();
    }

}
