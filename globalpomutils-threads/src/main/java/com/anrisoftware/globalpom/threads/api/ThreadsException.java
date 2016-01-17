/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-threads.
 *
 * globalpomutils-threads is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-threads is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-threads. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.threads.api;

import java.util.Map;

import com.anrisoftware.globalpom.exceptions.Context;

/**
 * Threads exception.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class ThreadsException extends Exception {

    private final Context<ThreadsException> context;

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public ThreadsException(String message, Throwable cause) {
        super(message, cause);
        this.context = new Context<ThreadsException>(this);
    }

    /**
     * @see Exception#Exception(String)
     */
    public ThreadsException(String message) {
        super(message);
        this.context = new Context<ThreadsException>(this);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public ThreadsException(Object message, Throwable cause) {
        super(message.toString(), cause);
        this.context = new Context<ThreadsException>(this);
    }

    /**
     * @see Exception#Exception(String)
     */
    public ThreadsException(Object message) {
        super(message.toString());
        this.context = new Context<ThreadsException>(this);
    }

    /**
     * @see Context#addContext(String, Object)
     */
    public ThreadsException add(String name, Object value) {
        context.addContext(name, value);
        return this;
    }

    /**
     * @see Context#addContext(String, Object)
     */
    public ThreadsException add(Object name, Object value) {
        context.addContext(name.toString(), value);
        return this;
    }

    /**
     * @see Context#getContext()
     */
    public Map<String, Object> getContext() {
        return context.getContext();
    }

    @Override
    public String getMessage() {
        return context.message(super.getMessage());
    }

    @Override
    public String getLocalizedMessage() {
        return context.localizedMessage(super.getMessage());
    }

    @Override
    public String toString() {
        return context.toString();
    }
}
