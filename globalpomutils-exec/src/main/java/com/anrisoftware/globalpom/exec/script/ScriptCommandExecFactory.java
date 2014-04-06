package com.anrisoftware.globalpom.exec.script;

import com.anrisoftware.globalpom.exec.api.CommandExecFactory;

/**
 * Factory to create a script exec that is created from a template.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public interface ScriptCommandExecFactory {

    /**
     * Creates the external command executer.
     * 
     * @param execFactory
     *            the {@link CommandExecFactory}.
     * 
     * @return the {@link ScriptCommandExec}.
     */
    ScriptCommandExec create(CommandExecFactory execFactory);
}
