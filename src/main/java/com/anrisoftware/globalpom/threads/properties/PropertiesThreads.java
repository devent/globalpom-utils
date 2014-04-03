/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.threads.properties;

import static com.anrisoftware.globalpom.threads.properties.PropertiesThreadsModule.getPropertiesThreadsFactory;

import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.Duration;

import com.anrisoftware.globalpom.threads.api.ListenableFuture;
import com.anrisoftware.globalpom.threads.api.ThreadingPolicy;
import com.anrisoftware.globalpom.threads.api.Threads;
import com.anrisoftware.globalpom.threads.api.ThreadsException;
import com.anrisoftware.globalpom.threads.watchdog.ThreadsWatchdog;
import com.anrisoftware.propertiesutils.ContextProperties;

/**
 * Loads the thread pool properties from a properties file.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class PropertiesThreads implements Threads {

    /**
     * @see PropertiesThreadsFactory#create()
     * 
     * @since 1.11
     */
    public static PropertiesThreads createPropertiesThreads() {
        return getPropertiesThreadsFactory().create();
    }

    @Inject
    private PropertiesThreadsLogger log;

    @Inject
    private CachedThreadingPropertiesFactory cachedFactory;

    @Inject
    private ThreadingPropertiesFactory propertiesFactory;

    @Inject
    private FixedThreadingPropertiesFactory fixedFactory;

    @Inject
    private SingleThreadingPropertiesFactory singleFactory;

    @Inject
    private ThreadsWatchdog watchdog;

    private ContextProperties properties;

    private String name;

    private ExecutorService executor;

    /**
     * Sets the properties for the thread pool.
     * 
     * @param properties
     *            the {@link Properties}.
     */
    public void setProperties(Properties properties) {
        log.checkProperties(this, properties);
        this.properties = new ContextProperties(this, properties);
    }

    @Override
    public void setName(String name) throws ThreadsException {
        log.checkName(this, name);
        String oldValue = this.name;
        this.name = name;
        if (!name.equals(oldValue)) {
            executor = createExecutor();
            watchdog.setExecutor(executor);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    private ExecutorService createExecutor() throws ThreadsException {
        ThreadingProperties p = propertiesFactory.create(properties, name);
        ThreadFactory factory = p.getThreadFactory(null);
        ThreadingPolicy policy = p.getPolicy();
        switch (policy) {
        case CACHED:
            return createCachedPool(factory);
        case FIXED:
            return createFixedPool(factory);
        case SINGLE:
            return createSinglePool(factory);
        default:
            throw log.invalidPolicy(this, policy);
        }
    }

    private ExecutorService createSinglePool(ThreadFactory factory) {
        return singleFactory.create(properties, name).createExecutorService(
                factory);
    }

    private ExecutorService createCachedPool(ThreadFactory factory) {
        return cachedFactory.create(properties, name).createExecutorService(
                factory);
    }

    private ExecutorService createFixedPool(ThreadFactory factory) {
        FixedThreadingProperties fixedProperties = fixedFactory.create(
                properties, name);
        int maxThreads = fixedProperties.getMaxThreads();
        return fixedProperties.createExecutorService(factory, maxThreads);
    }

    /**
     * Unsupported.
     */
    @Override
    public void execute(Runnable command) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return executor.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executor.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executor.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        return executor.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return watchdog.submit(task);
    }

    @Override
    public <V> ListenableFuture<V> submit(Callable<V> callable,
            PropertyChangeListener... listeners) {
        return watchdog.submit(callable, listeners);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return watchdog.submit(task, result);
    }

    @Override
    public <V> ListenableFuture<V> submit(Runnable runable, V result,
            PropertyChangeListener... listeners) {
        return watchdog.submit(runable, result, listeners);
    }

    @Override
    public Future<?> submit(Runnable runable) {
        return watchdog.submit(runable, null);
    }

    /**
     * Unsupported.
     */
    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<Future<T>> invokeAll(
            Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return executor.invokeAll(tasks, timeout, unit);
    }

    /**
     * Unsupported.
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported.
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
            long timeout, TimeUnit unit) throws InterruptedException,
            ExecutionException, TimeoutException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Future<?>> getTasks() {
        return watchdog.getTasks();
    }

    @Override
    public void waitForTasks() throws InterruptedException {
        watchdog.waitForTasks();
    }

    @Override
    public List<Future<?>> waitForTasks(Duration timeout)
            throws InterruptedException {
        return watchdog.waitForTasks(timeout);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).toString();
    }
}
