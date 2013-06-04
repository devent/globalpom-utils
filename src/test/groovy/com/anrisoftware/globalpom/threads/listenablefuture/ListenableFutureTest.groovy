package com.anrisoftware.globalpom.threads.listenablefuture

import static com.anrisoftware.globalpom.threads.api.ListenableFuture.*
import static java.util.concurrent.TimeUnit.*

import java.beans.PropertyChangeListener
import java.util.concurrent.Executors

import org.junit.Test

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
