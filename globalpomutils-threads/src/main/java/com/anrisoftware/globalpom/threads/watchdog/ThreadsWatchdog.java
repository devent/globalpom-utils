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
package com.anrisoftware.globalpom.threads.watchdog;

import static java.util.Collections.synchronizedList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.joda.time.Duration;

import com.anrisoftware.globalpom.threads.api.ListenableFuture;
import com.anrisoftware.globalpom.threads.api.ListenableFuture.Status;
import com.anrisoftware.globalpom.threads.api.Threads;
import com.anrisoftware.globalpom.threads.listenablefuture.DefaultListenableFuture;

/**
 * Keeps track of the submitted tasks.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class ThreadsWatchdog {

    private final ThreadsWatchdogLogger log;

    private volatile List<Future<?>> tasks;

    private volatile ExecutorService executor;

    private volatile boolean notified;

    @Inject
    ThreadsWatchdog(ThreadsWatchdogLogger logger) {
        this.log = logger;
        this.tasks = synchronizedList(new ArrayList<Future<?>>());
        this.notified = false;
    }

    /**
     * Sets the executor service to submit tasks.
     *
     * @param executor
     *            the {@link ExecutorService}.
     */
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    /**
     * @see Threads#submit(Callable, PropertyChangeListener...)
     */
    public <V> DefaultListenableFuture<V> submit(Callable<V> callable,
            PropertyChangeListener... listeners) {
        DefaultListenableFuture<V> futureTask;
        futureTask = new DefaultListenableFuture<V>(callable);
        return submit(futureTask, listeners);
    }

    /**
     * @see Threads#submit(Runnable, Object, PropertyChangeListener...)
     */
    public <V> ListenableFuture<V> submit(Runnable runable, V result,
            PropertyChangeListener... listeners) {
        DefaultListenableFuture<V> futureTask;
        futureTask = new DefaultListenableFuture<V>(runable, result);
        return submit(futureTask, listeners);
    }

    private <V> DefaultListenableFuture<V> submit(
            final DefaultListenableFuture<V> task,
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
            notified = true;
            notify();
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
     */
    public List<Future<?>> waitForTasks(Duration timeout)
            throws InterruptedException {
        while (tasks.size() > 0) {
            synchronized (this) {
                wait(timeout.getMillis());
                if (notified) {
                    notified = false;
                } else {
                    break;
                }
            }
        }
        return tasks;
    }

}
