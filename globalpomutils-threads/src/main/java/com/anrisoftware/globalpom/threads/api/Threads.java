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
package com.anrisoftware.globalpom.threads.api;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.joda.time.Duration;

/**
 * Submits tasks for execution.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public interface Threads extends ExecutorService {

    /**
     * Sets the name of the threads pools. The properties of the thread pool are
     * loaded and a new pool is created.
     * 
     * @param name
     *            the {@link String} name.
     * 
     * @throws ThreadsException
     *             if there was any error load the property of the thread pool.
     * 
     * @throws NullPointerException
     *             if the specified name is {@code null}.
     * 
     * @throws IllegalArgumentException
     *             if the specified name is empty.
     */
    void setName(String name) throws ThreadsException;

    /**
     * Returns the name of the threads pool.
     * 
     * @return the {@link String} name.
     */
    String getName();

    /**
     * Submits the task for execution. The specified property change listeners
     * are informed when the task is running and when the task finished.
     * 
     * @see ExecutorService#submit(Callable)
     * 
     * @param listeners
     *            {@link PropertyChangeListener} listeners that are informed
     *            when the task finishes.
     */
    <V> ListenableFuture<V> submit(Callable<V> callable,
            PropertyChangeListener... listeners);

    /**
     * Submits the task for execution. The specified property change listeners
     * are informed when the task is running and when the task finished.
     * 
     * @see ExecutorService#submit(Runnable, Object)
     * 
     * @param listeners
     *            {@link PropertyChangeListener} listeners that are informed
     *            when the task finishes.
     */
    <V> ListenableFuture<V> submit(Runnable runable, V result,
            PropertyChangeListener... listeners);

    /**
     * Returns a list of submitted and still running tasks.
     * 
     * @return {@link List} of the submitted and still running tasks.
     */
    List<Future<?>> getTasks();

    /**
     * Waits for all tasks to finish.
     * 
     * @throws InterruptedException
     *             if interrupted while waiting.
     */
    void waitForTasks() throws InterruptedException;

    /**
     * Waits for all tasks to finish up until the specified timeout.
     * 
     * @param timeout
     *            the {@link Duration} timeout to wait for one task to finish.
     * 
     * @return the tasks {@link List} that was not finished before the timeout.
     * 
     * @throws InterruptedException
     *             if interrupted while waiting.
     */
    List<Future<?>> waitForTasks(Duration timeout) throws InterruptedException;
}
