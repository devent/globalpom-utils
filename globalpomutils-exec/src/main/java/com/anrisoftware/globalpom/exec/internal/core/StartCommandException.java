package com.anrisoftware.globalpom.exec.internal.core;

import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;

@SuppressWarnings("serial")
public class StartCommandException extends CommandExecException {

    public StartCommandException(DefaultProcessTask processTask,
            Throwable cause, CommandLine commandLine) {
        super("Error start command", cause);
        addContextValue("process-task", processTask);
        addContextValue("command-line", commandLine);
    }

}
