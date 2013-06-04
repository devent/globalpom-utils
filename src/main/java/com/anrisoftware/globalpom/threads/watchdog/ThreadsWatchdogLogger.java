package com.anrisoftware.globalpom.threads.watchdog;

import java.util.concurrent.Future;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.anrisoftware.globalpom.threads.listenablefuture.DefaultListenableFuture;

/**
 * Logging messages for {@link ThreadsWatchdog}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@Singleton
class ThreadsWatchdogLogger extends AbstractLogger {

	/**
	 * Create logger for {@link ThreadsWatchdog}.
	 */
	public ThreadsWatchdogLogger() {
		super(ThreadsWatchdog.class);
	}

	void taskSubmitted(DefaultListenableFuture<?> task) {
		log.debug("Task {} submitted.", task);
	}

	void taskDone(Future<?> future) {
		log.debug("Task {} done.", future);
	}
}
