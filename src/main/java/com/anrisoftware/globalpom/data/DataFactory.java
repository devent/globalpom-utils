package com.anrisoftware.globalpom.data;

/**
 * Factory to create the matrix data.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.11
 */
public interface DataFactory {

    /**
     * Creates the matrix data.
     * 
     * @return the {@link Data}.
     */
    Data create();
}
