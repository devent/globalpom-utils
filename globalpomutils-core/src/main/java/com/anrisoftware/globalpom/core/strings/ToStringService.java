package com.anrisoftware.globalpom.core.strings;

import java.util.Map;

/**
 * Converts an argument to a String.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface ToStringService {

    /**
     * Converts the specified argument to a {@link String}.
     *
     * @throws NullPointerException if the argument is {@code null}.
     */
    String toString(Map<String, Object> args, String name);

    /**
     * Converts the specified argument to a {@link String}.
     *
     * @throws NullPointerException if the argument is {@code null}.
     */
    String toString(Object arg, String name);
}
