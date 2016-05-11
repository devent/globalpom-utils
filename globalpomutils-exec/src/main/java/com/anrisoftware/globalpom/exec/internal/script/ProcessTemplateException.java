package com.anrisoftware.globalpom.exec.internal.script;

import com.anrisoftware.globalpom.exec.external.core.CommandExecException;
import com.anrisoftware.resources.external.ResourcesException;

@SuppressWarnings("serial")
public class ProcessTemplateException extends CommandExecException {

    public ProcessTemplateException(ScriptCommandLine commandLine,
            ResourcesException e) {
        super("Error process template", e);
    }

}
