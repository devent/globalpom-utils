package com.anrisoftware.globalpom.exec.api;

import java.util.Map;

import com.anrisoftware.globalpom.exceptions.Context;

/**
 * Exception thrown from {@link CommandExec}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class CommandExecException extends Exception {

    private final Context<CommandExecException> context;

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public CommandExecException(String message, Throwable cause) {
        super(message, cause);
        this.context = new Context<CommandExecException>(this);
    }

    /**
     * @see Exception#Exception(String)
     */
    public CommandExecException(String message) {
        super(message);
        this.context = new Context<CommandExecException>(this);
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public CommandExecException(Object message, Throwable cause) {
        super(message.toString(), cause);
        this.context = new Context<CommandExecException>(this);
    }

    /**
     * @see Exception#Exception(String)
     */
    public CommandExecException(Object message) {
        super(message.toString());
        this.context = new Context<CommandExecException>(this);
    }

    /**
     * @see Context#addContext(String, Object)
     */
    public CommandExecException add(String name, Object value) {
        context.addContext(name, value);
        return this;
    }

    /**
     * @see Context#addContext(String, Object)
     */
    public CommandExecException add(Object name, Object value) {
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
