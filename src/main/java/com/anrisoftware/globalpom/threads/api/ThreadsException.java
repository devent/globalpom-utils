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
package com.anrisoftware.globalpom.threads.api;

import java.util.Map;

import com.anrisoftware.globalpom.exceptions.Context;

@SuppressWarnings("serial")
public class ThreadsException extends Exception {

	private Context<ThreadsException> context;

	public ThreadsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ThreadsException(String message) {
		super(message);
	}

	public ThreadsException addContext(String name, Object value) {
		return context.addContext(name, value);
	}

	public Map<String, Object> getContext() {
		return context.getContext();
	}

}
