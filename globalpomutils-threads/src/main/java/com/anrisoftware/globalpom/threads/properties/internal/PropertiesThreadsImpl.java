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

package com.anrisoftware.globalpom.threads.properties.internal;

import static com.anrisoftware.globalpom.threads.properties.internal.PropertiesThreadsModule.getPropertiesThreadsFactory;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

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

import com.anrisoftware.globalpom.threads.external.core.ListenableFuture;
import com.anrisoftware.globalpom.threads.external.core.ThreadingPolicy;
import com.anrisoftware.globalpom.threads.external.core.ThreadsException;
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreads;
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsFactory;
import com.anrisoftware.globalpom.threads.watchdog.ThreadsWatchdog;
import com.anrisoftware.propertiesutils.ContextProperties;

/**
 * Loads the thread pool properties from a properties file.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class PropertiesThreadsImpl implements PropertiesThreads {

    /**
     * @see PropertiesThreadsFactory#create()
     *
     * @since 1.11
     */
    public static PropertiesThreads createPropertiesThreads() {
        return getPropertiesThreadsFactory().create();
    }

    @Inject
    private CachedThreadingPropertiesFactory cachedFactory;

    @Inject
    private DefaultThreadingPropertiesFactory propertiesFactory;

    @Inject
    private FixedThreadingPropertiesFactory fixedFactory;

    @Inject
    private SingleThreadingPropertiesFactory singleFactory;

    @Inject
    private ThreadsWatchdog watchdog;

    private ContextProperties properties;

    private String name;

    private ExecutorService executor;

    @Override
    public void setProperties(Properties properties) {
        notNull(properties, "properties = null");
        this.properties = new ContextProperties(PropertiesThreads.class, properties);
    }

    @Override
    public void setName(String name) throws ThreadsException {
        notBlank(name, "name blank");
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
            throw new InvalidThreadPoolPolicyException(policy);
        }
    }

    private ExecutorService createSinglePool(ThreadFactory factory) {
        return singleFactory.create(properties, name).createExecutorService(factory);
    }

    private ExecutorService createCachedPool(ThreadFactory factory) {
        return cachedFactory.create(properties, name).createExecutorService(factory);
    }

    private ExecutorService createFixedPool(ThreadFactory factory) {
        FixedThreadingProperties fixedProperties = fixedFactory.create(properties, name);
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
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executor.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return watchdog.submit(task);
    }

    @Override
    public <V> ListenableFuture<V> submit(Callable<V> callable, PropertyChangeListener... listeners) {
        return watchdog.submit(callable, listeners);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return watchdog.submit(task, result);
    }

    @Override
    public <V> ListenableFuture<V> submit(Runnable runable, V result, PropertyChangeListener... listeners) {
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
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return executor.invokeAll(tasks, timeout, unit);
    }

    /**
     * Unsupported.
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported.
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
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
    public List<Future<?>> waitForTasks(Duration timeout) throws InterruptedException {
        return watchdog.waitForTasks(timeout);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).toString();
    }
}
