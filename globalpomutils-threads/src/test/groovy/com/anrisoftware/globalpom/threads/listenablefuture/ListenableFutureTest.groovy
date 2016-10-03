/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.threads.listenablefuture

import static com.anrisoftware.globalpom.threads.external.core.ListenableFuture.*
import static java.util.concurrent.TimeUnit.*

import java.beans.PropertyChangeListener
import java.util.concurrent.Executors

import org.junit.Test

import com.anrisoftware.globalpom.threads.external.listenablefuture.DefaultListenableFuture

/**
 * @see DefaultListenableFuture
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class ListenableFutureTest {

    @Test
    void "submit listenable task"() {
        def taskStatus = null
        def listener = { evt -> taskStatus = evt.newValue } as PropertyChangeListener
        boolean taskRun = false
        def task = { taskRun = true }
        def future = new DefaultListenableFuture(task)
        future.addPropertyChangeListener listener
        def service = Executors.newCachedThreadPool()

        service.submit(future)
        service.shutdown()
        service.awaitTermination 100, MILLISECONDS
        assert taskRun == true
        assert taskStatus == Status.DONE
    }
}
