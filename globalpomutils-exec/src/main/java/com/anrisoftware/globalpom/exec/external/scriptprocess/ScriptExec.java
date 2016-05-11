package com.anrisoftware.globalpom.exec.external.scriptprocess;

import java.util.concurrent.Callable;

import com.anrisoftware.globalpom.exec.external.core.ProcessTask;

/**
 * Executes the script from a template.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
public interface ScriptExec extends Callable<ProcessTask> {

    /**
     * Returns the script that was executed.
     *
     * @return the script {@link String}.
     */
    String getScriptString();

}
