/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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

	private static final String TASK_DONE = "Task {} done.";
	private static final String TASK_SUBMITTED = "Task {} submitted.";

	/**
	 * Create logger for {@link ThreadsWatchdog}.
	 */
	public ThreadsWatchdogLogger() {
		super(ThreadsWatchdog.class);
	}

	void taskSubmitted(DefaultListenableFuture<?> task) {
		log.debug(TASK_SUBMITTED, task);
	}

	void taskDone(Future<?> future) {
		log.debug(TASK_DONE, future);
	}
}
