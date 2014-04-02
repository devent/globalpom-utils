package com.anrisoftware.globalpom.exec.api;

import java.util.concurrent.Callable;

/**
 * Executes command task.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface ProcessTask extends Callable<ProcessTask> {

    /**
     * Executes the command.
     */
    @Override
    ProcessTask call() throws CommandExecException;

    /**
     * Returns the standard output of the process after the process finished.
     * 
     * @return the {@link String} standard output.
     */
    String getOut();

    /**
     * Returns the standard error output of the process after the process
     * finished.
     * 
     * @return the {@link String} standard error output.
     */
    String getErr();

    /**
     * Returns the exit value of the process after the process finished.
     * 
     * @return the exit value.
     */
    int getExitValue();
}
