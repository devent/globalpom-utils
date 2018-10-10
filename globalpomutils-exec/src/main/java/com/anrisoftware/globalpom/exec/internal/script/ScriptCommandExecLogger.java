package com.anrisoftware.globalpom.exec.internal.script;

import java.io.IOException;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link ScriptCommandExec}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 4.5.0
 */
class ScriptCommandExecLogger extends AbstractLogger {

    /**
     * Sets the context of the logger to {@link ScriptCommandExec}.
     */
    public ScriptCommandExecLogger() {
        super(ScriptCommandExec.class);
    }

    void deleteScriptFileError(IOException e) {
        log.error(e.getMessage(), e);
    }
}
