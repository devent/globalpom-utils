package com.anrisoftware.globalpom.exec.internal.core;

import java.io.IOException;

import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;

@SuppressWarnings("serial")
public class ExecuteCommandException extends CommandExecException {

    public ExecuteCommandException(DefaultCommandExec commandExec,
            IOException e, CommandLine commandLine) {
        super("Error exec command", e);
        addContextValue("command-exec", commandExec);
        addContextValue("command-line", commandLine);
    }

}
