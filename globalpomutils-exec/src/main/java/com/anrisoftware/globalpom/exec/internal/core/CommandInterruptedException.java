package com.anrisoftware.globalpom.exec.internal.core;

import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;

@SuppressWarnings("serial")
public class CommandInterruptedException extends CommandExecException {

    public CommandInterruptedException(DefaultProcessTask processTask,
            InterruptedException e, CommandLine commandLine) {
        super("Command interrupted", e);
        addContextValue("process-task", processTask);
        addContextValue("command-line", commandLine);
    }

}
