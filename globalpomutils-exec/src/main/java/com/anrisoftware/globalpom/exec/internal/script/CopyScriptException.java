package com.anrisoftware.globalpom.exec.internal.script;

import java.io.IOException;

import com.anrisoftware.globalpom.exec.external.core.CommandExecException;

@SuppressWarnings("serial")
public class CopyScriptException extends CommandExecException {

    public CopyScriptException(ScriptCommandLine commandLine, IOException e) {
        super("Error copy script", e);
    }

}
