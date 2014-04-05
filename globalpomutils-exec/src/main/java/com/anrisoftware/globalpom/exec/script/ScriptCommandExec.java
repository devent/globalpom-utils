/*
 * Copyright 2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.exec.script;

import static com.anrisoftware.globalpom.exec.core.DefaultProcessModule.getCommandExecFactory;

import java.beans.PropertyChangeListener;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.exec.api.CommandExec;
import com.anrisoftware.globalpom.exec.api.CommandExecException;
import com.anrisoftware.globalpom.exec.api.CommandExecFactory;
import com.anrisoftware.globalpom.exec.api.CommandInput;
import com.anrisoftware.globalpom.exec.api.CommandOutput;
import com.anrisoftware.globalpom.exec.api.CommandLine;
import com.anrisoftware.globalpom.exec.api.ProcessTask;
import com.anrisoftware.globalpom.threads.api.Threads;

/**
 * Executes a script created from a loaded template.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public class ScriptCommandExec implements CommandExec {

    private static final String EXEC = "exec";

    /**
     * @see CommandExecFactory#create()
     */
    public static CommandExec createCommandExec() {
        return getCommandExecFactory().create();
    }

    private final CommandExec exec;

    @Inject
    private ScriptCommandExecLogger log;

    @Inject
    ScriptCommandExec(CommandExecFactory execFactory) {
        this.exec = execFactory.create();
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
    public Future<ProcessTask> exec(CommandLine commandLine,
            PropertyChangeListener... listeners) throws CommandExecException {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(EXEC, exec).toString();
    }
}
