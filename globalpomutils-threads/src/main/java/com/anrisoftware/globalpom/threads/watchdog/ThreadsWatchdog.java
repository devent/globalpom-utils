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
package com.anrisoftware.globalpom.threads.watchdog;

import static java.util.Collections.synchronizedList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import org.joda.time.Duration;

import com.anrisoftware.globalpom.threads.external.core.ListenableFuture;
import com.anrisoftware.globalpom.threads.external.core.ListenableFuture.Status;
import com.anrisoftware.globalpom.threads.external.core.Threads;
import com.anrisoftware.globalpom.threads.external.listenablefuture.DefaultListenableFuture;

/**
 * Keeps track of the submitted tasks.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class ThreadsWatchdog {

    private final ThreadsWatchdogLogger log;

    private List<Future<?>> tasks;

    private ExecutorService executor;

    private AtomicBoolean notified;

    @Inject
    ThreadsWatchdog(ThreadsWatchdogLogger logger) {
        this.log = logger;
        this.tasks = synchronizedList(new ArrayList<Future<?>>());
        this.notified = new AtomicBoolean(false);
    }

    /**
     * Sets the executor service to submit tasks.
     *
     * @param executor the {@link ExecutorService}.
     */
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    /**
     * @see Threads#submit(Callable, PropertyChangeListener...)
     *
     * @param callable  the {@link Callable}
     *
     * @param listeners the {@link PropertyChangeListener} listeners.
     *
     * @param <V>       the task type.
     *
     * @return the task.
     */
    public <V> DefaultListenableFuture<V> submit(Callable<V> callable, PropertyChangeListener... listeners) {
        DefaultListenableFuture<V> futureTask;
        futureTask = new DefaultListenableFuture<V>(callable);
        return submit(futureTask, listeners);
    }

    /**
     * @see Threads#submit(Runnable, Object, PropertyChangeListener...)
     *
     * @param runable   the {@link Runnable}
     *
     * @param result    the result.
     *
     * @param listeners the {@link PropertyChangeListener} listeners.
     *
     * @param <V>       the result type.
     *
     * @return the task.
     */
    public <V> ListenableFuture<V> submit(Runnable runable, V result, PropertyChangeListener... listeners) {
        DefaultListenableFuture<V> futureTask;
        futureTask = new DefaultListenableFuture<V>(runable, result);
        return submit(futureTask, listeners);
    }

    private <V> DefaultListenableFuture<V> submit(final DefaultListenableFuture<V> task,
            PropertyChangeListener[] listeners) {
        for (PropertyChangeListener l : listeners) {
            task.addPropertyChangeListener(l);
        }
        task.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Status status = (Status) evt.getNewValue();
                if (status == Status.DONE) {
                    tasks.remove(task);
                    log.taskDone(task);
                    unlockWait();
                }
            }

        });
        tasks.add(task);
        executor.submit(task);
        log.taskSubmitted(task);
        return task;
    }

    private void unlockWait() {
        synchronized (this) {
            notified.set(true);
            notifyAll();
        }
    }

    /**
     * @see Threads#getTasks()
     *
     * @return a copy of the submitted tasks.
     */
    public List<Future<?>> getTasks() {
        synchronized (tasks) {
            return new ArrayList<Future<?>>(tasks);
        }
    }

    /**
     * @see Threads#waitForTasks()
     *
     * @throws InterruptedException if any of the tasks was interrupted.
     */
    public void waitForTasks() throws InterruptedException {
        while (tasks.size() > 0) {
            synchronized (this) {
                wait(100);
            }
        }
    }

    /**
     * @see Threads#waitForTasks(Duration)
     *
     * @param timeout the {@link Duration} timeout duration.
     *
     * @return the {@link List} of {@link Future} futures.
     *
     * @throws InterruptedException if any of the tasks was interrupted.
     */
    public List<Future<?>> waitForTasks(Duration timeout) throws InterruptedException {
        while (tasks.size() > 0) {
            synchronized (this) {
                wait(timeout.getMillis());
                if (!notified.compareAndSet(true, false)) {
                    break;
                }
            }
        }
        return tasks;
    }

}
