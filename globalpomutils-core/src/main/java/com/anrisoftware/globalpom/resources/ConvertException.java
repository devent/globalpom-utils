/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.resources;

import java.util.Map;

import com.anrisoftware.globalpom.exceptions.Context;

/**
 * Exception if the input can not be converted to URL or URI.
 * 
 * @see StringToURI
 * @see ToURL
 * @see ToURI
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class ConvertException extends RuntimeException {

    private final Context<ConvertException> context;

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public ConvertException(String message, Throwable cause) {
        super(message, cause);
        this.context = new Context<ConvertException>(this);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public ConvertException(Object message, Throwable cause) {
        super(message.toString(), cause);
        this.context = new Context<ConvertException>(this);
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public ConvertException(String message) {
        super(message);
        this.context = new Context<ConvertException>(this);
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public ConvertException(Object message) {
        super(message.toString());
        this.context = new Context<ConvertException>(this);
    }

    /**
     * @see Context#addContext(String, Object)
     */
    public ConvertException add(String name, Object value) {
        return context.addContext(name, value);
    }

    /**
     * @see Context#addContext(String, Object)
     */
    public ConvertException add(Object name, Object value) {
        return context.addContext(name.toString(), value);
    }

    /**
     * @see Context#getContext()
     */
    public Map<String, Object> getContext() {
        return context.getContext();
    }

    @Override
    public String toString() {
        return context.toString();
    }

}
