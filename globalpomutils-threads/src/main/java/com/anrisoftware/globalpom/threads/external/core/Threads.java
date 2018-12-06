/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anrisoftware.globalpom.threads.external.core;

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
     * @param name the {@link String} name.
     *
     * @throws ThreadsException         if there was any error load the property of
     *                                  the thread pool.
     *
     * @throws NullPointerException     if the specified name is {@code null}.
     *
     * @throws IllegalArgumentException if the specified name is empty.
     */
    void setName(String name) throws ThreadsException;

    /**
     * Returns the name of the threads pool.
     *
     * @return the {@link String} name.
     */
    String getName();

    /**
     * Submits the task for execution. The specified property change listeners are
     * informed when the task is running and when the task finished.
     *
     * @see ExecutorService#submit(Callable)
     * @see ListenableFuture.Status
     *
     * @param listeners {@link PropertyChangeListener} listeners that are informed
     *                  when the task finishes.
     */
    <V> ListenableFuture<V> submit(Callable<V> callable, PropertyChangeListener... listeners);

    /**
     * Submits the task for execution. The specified property change listeners are
     * informed when the task is running and when the task finished.
     *
     * @see ExecutorService#submit(Runnable, Object)
     * @see ListenableFuture.Status
     *
     * @param listeners {@link PropertyChangeListener} listeners that are informed
     *                  when the task finishes.
     */
    <V> ListenableFuture<V> submit(Runnable runable, V result, PropertyChangeListener... listeners);

    /**
     * Returns a list of submitted and still running tasks.
     *
     * @return {@link List} of the submitted and still running tasks.
     */
    List<Future<?>> getTasks();

    /**
     * Waits for all tasks to finish.
     *
     * @throws InterruptedException if interrupted while waiting.
     */
    void waitForTasks() throws InterruptedException;

    /**
     * Waits for all tasks to finish up until the specified timeout.
     *
     * @param timeout the {@link Duration} timeout to wait for one task to finish.
     *
     * @return the tasks {@link List} that was not finished before the timeout.
     *
     * @throws InterruptedException if interrupted while waiting.
     */
    List<Future<?>> waitForTasks(Duration timeout) throws InterruptedException;
}
