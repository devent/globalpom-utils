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
package com.anrisoftware.globalpom.threads.listenablefuture;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.threads.api.ListenableFuture;

/**
 * Informs property change listener when the task is finish.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class DefaultListenableFuture<V> extends FutureTask<V> implements
		ListenableFuture<V> {

	private final PropertyChangeSupport propertySupport;

	private Status status;

	private Callable<V> callable;

	private Runnable runnable;

	private V result;

	/**
	 * @see FutureTask#FutureTask(Callable)
	 */
	public DefaultListenableFuture(Callable<V> callable) {
		super(callable);
		this.callable = callable;
		this.propertySupport = new PropertyChangeSupport(this);
		this.status = Status.RUNNING;
	}

	/**
	 * @see FutureTask#FutureTask(Runnable, Object)
	 */
	public DefaultListenableFuture(Runnable runnable, V result) {
		super(runnable, result);
		this.runnable = runnable;
		this.result = result;
		this.propertySupport = new PropertyChangeSupport(this);
	}

	@Override
	protected void done() {
		Status oldValue = this.status;
		status = Status.DONE;
		propertySupport.firePropertyChange(STATUS_PROPERTY, oldValue, status);
	}

	@Override
	public String toString() {
		ToStringBuilder b = new ToStringBuilder(this);
		if (callable != null) {
			b.append(callable);
		} else {
			b.append(runnable).append(result);
		}
		return b.toString();
	}

	/**
	 * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
	 * @see #STATUS_PROPERTY
	 */
	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(listener);
	}

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
	 * @see #STATUS_PROPERTY
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(listener);
	}

	/**
	 * @see PropertyChangeSupport#addPropertyChangeListener(String,
	 *      PropertyChangeListener)
	 * @see #STATUS_PROPERTY
	 */
	@Override
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(String,
	 *      PropertyChangeListener)
	 * @see #STATUS_PROPERTY
	 */
	@Override
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(propertyName, listener);
	}

}
