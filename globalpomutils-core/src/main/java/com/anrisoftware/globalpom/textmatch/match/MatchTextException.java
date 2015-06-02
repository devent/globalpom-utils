/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.textmatch.match;

import java.util.Map;

import com.anrisoftware.globalpom.exceptions.Context;

/**
 * Exception for match text.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@SuppressWarnings("serial")
public class MatchTextException extends Exception {

    private final Context<MatchTextException> context;

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public MatchTextException(String message, Throwable cause) {
        super(message, cause);
        this.context = new Context<MatchTextException>(this);
    }

    /**
     * @see Exception#Exception(String)
     */
    public MatchTextException(String message) {
        super(message);
        this.context = new Context<MatchTextException>(this);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public MatchTextException(Object message, Throwable cause) {
        super(message.toString(), cause);
        this.context = new Context<MatchTextException>(this);
    }

    /**
     * @see Exception#Exception(String)
     */
    public MatchTextException(Object message) {
        super(message.toString());
        this.context = new Context<MatchTextException>(this);
    }

    /**
     * @see Context#addContext(String, Object)
     */
    public MatchTextException add(String name, Object value) {
        context.addContext(name, value);
        return this;
    }

    /**
     * @see Context#addContext(String, Object)
     */
    public MatchTextException add(Object name, Object value) {
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
