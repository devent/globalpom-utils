package com.anrisoftware.globalpom.exec.api;

/**
 * Factory to create the external command executer.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface CommandExecFactory {

    /**
     * Creates the external command executer.
     * 
     * @return the {@link CommandExec}.
     */
    CommandExec create();
}
