package com.anrisoftware.globalpom.format.measurement;

/**
 * Factory to create a new value renderer.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.1
 */
public interface ValueRendererFactory {

    /**
     * Creates a new value renderer.
     * 
     * @return the {@link ValueRenderer}.
     */
    ValueRenderer create();
}
