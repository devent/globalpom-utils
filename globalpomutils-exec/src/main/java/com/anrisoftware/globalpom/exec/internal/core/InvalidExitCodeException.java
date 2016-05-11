package com.anrisoftware.globalpom.exec.internal.core;

import java.util.Arrays;

import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.globalpom.exec.external.core.CommandLine;

@SuppressWarnings("serial")
public class InvalidExitCodeException extends CommandExecException {

    public InvalidExitCodeException(DefaultProcessTask processTask, int ret,
            int[] exitCodes, CommandLine commandLine) {
        super("Error exit code");
        addContextValue("process-task", processTask);
        addContextValue("command-line", commandLine);
        addContextValue("return-code", ret);
        addContextValue("exit-codes", Arrays.toString(exitCodes));
    }

}
