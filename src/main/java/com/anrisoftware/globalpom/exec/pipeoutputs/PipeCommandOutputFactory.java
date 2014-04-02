package com.anrisoftware.globalpom.exec.pipeoutputs;

import java.io.OutputStream;

/**
 * Factory to create the pipe output.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface PipeCommandOutputFactory {

    PipeCommandOutput create(OutputStream output);

}
