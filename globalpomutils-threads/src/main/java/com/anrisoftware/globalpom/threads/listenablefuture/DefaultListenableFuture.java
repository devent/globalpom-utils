/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.threads.listenablefuture;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anrisoftware.globalpom.threads.api.ListenableFuture;

/**
 * Informs property change listener when the task is finish.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class DefaultListenableFuture<V> extends FutureTask<V> implements
        ListenableFuture<V> {

    private static final Logger logger = LoggerFactory
            .getLogger(DefaultListenableFuture.class);

    private final PropertyChangeSupport p;

    private Status status;

    private Callable<V> callable;

    private Runnable runnable;

    private V result;

    /**
     * @see FutureTask#FutureTask(Callable)
     */
    public DefaultListenableFuture(Callable<V> callable) {
        super(createExceptionCallable(callable));
        this.callable = callable;
        this.p = new PropertyChangeSupport(this);
        this.status = Status.RUNNING;
    }

    /**
     * @see FutureTask#FutureTask(Runnable, Object)
     */
    public DefaultListenableFuture(Runnable runnable, V result) {
        super(createExceptionRunnable(runnable), result);
        this.runnable = runnable;
        this.result = result;
        this.p = new PropertyChangeSupport(this);
    }

    @Override
    protected void done() {
        Status oldValue = this.status;
        status = Status.DONE;
        p.firePropertyChange(STATUS_PROPERTY, oldValue, status);
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException,
            ExecutionException, TimeoutException {
        try {
            return super.get(timeout, unit);
        } catch (TimeoutException e) {
            Status oldValue = this.status;
            status = Status.TIMEOUT;
            p.firePropertyChange(STATUS_PROPERTY, oldValue, status);
            throw e;
        }
    }

    @Override
    public String toString() {
        ToStringBuilder b = new ToStringBuilder(this);
        if (callable != null) {
            b.append(callable);
        } else {
            b.append(runnable).append(result);
        }
        return b.toString();
    }

    private static <V> Callable<V> createExceptionCallable(
            final Callable<V> callable) {
        return new Callable<V>() {

            @Override
            public V call() throws Exception {
                try {
                    return callable.call();
                } catch (Exception e) {
                    logger.error("", e);
                    throw e;
                }
            }
        };
    }

    private static Runnable createExceptionRunnable(final Runnable runnable) {
        return new Runnable() {

            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        };
    }

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
     * @see #STATUS_PROPERTY
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        p.addPropertyChangeListener(listener);
    }

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
     * @see #STATUS_PROPERTY
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        p.removePropertyChangeListener(listener);
    }

    /**
     * @see PropertyChangeSupport#addPropertyChangeListener(String,
     *      PropertyChangeListener)
     * @see #STATUS_PROPERTY
     */
    @Override
    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        p.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * @see PropertyChangeSupport#removePropertyChangeListener(String,
     *      PropertyChangeListener)
     * @see #STATUS_PROPERTY
     */
    @Override
    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        p.removePropertyChangeListener(propertyName, listener);
    }

}
