/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
