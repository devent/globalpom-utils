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
package com.anrisoftware.globalpom.threads.watchdog

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import org.joda.time.Duration
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see ThreadsWatchdog
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class ThreadsWatchdogTest {

	@Test(timeout = 2000l)
	void "submit callable task"() {
		boolean taskRun = false
		def task = { Thread.sleep 1000; taskRun = true }
		def future = watchdog.submit(task)

		watchdog.waitForTasks()
		assert taskRun == true
	}

	@Test(timeout = 3000l)
	void "submit callable task +short timeout"() {
		boolean taskRun = false
		def task = { Thread.sleep 2000; taskRun = true }
		def future = watchdog.submit(task)

		def notFinished = watchdog.waitForTasks Duration.parse("PT1S")
		assert notFinished.size() == 1
		assert taskRun == false
	}

	@Test(timeout = 3000l)
	void "submit callable task +long timeout"() {
		boolean taskRun = false
		def task = { Thread.sleep 1000; taskRun = true }
		def future = watchdog.submit(task)

		def notFinished = watchdog.waitForTasks Duration.parse("PT2S")
		assert notFinished.size() == 0
		assert taskRun == true
	}

	ThreadsWatchdog watchdog

	@Before
	void setupWatchdog() {
		watchdog = injector.getInstance ThreadsWatchdog
		watchdog.setExecutor service
	}

	static Injector injector

	static ExecutorService service

	@BeforeClass
	static void setupService() {
		injector = Guice.createInjector()
		service = Executors.newCachedThreadPool()
	}
}
