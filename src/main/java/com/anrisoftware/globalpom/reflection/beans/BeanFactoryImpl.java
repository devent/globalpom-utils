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
package com.anrisoftware.globalpom.reflection.beans;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * Creates bean objects.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public class BeanFactoryImpl implements BeanFactory {

	private final BeanFactoryImplLogger log;

	/**
	 * Sets the logger
	 * 
	 * @param logger
	 *            the {@link BeanFactoryImplLogger}.
	 */
	@Inject
	BeanFactoryImpl(BeanFactoryImplLogger logger) {
		this.log = logger;
	}

	@Override
	public <T> T create(Class<T> type) {
		try {
			return ConstructorUtils.invokeConstructor(type);
		} catch (NoSuchMethodException e) {
			throw log.noSuchCtorError(e, type);
		} catch (IllegalAccessException e) {
			throw log.illegalAccessError(e, type);
		} catch (InvocationTargetException e) {
			throw log.invocationTargetError(e, type);
		} catch (InstantiationException e) {
			throw log.instantiationError(e, type);
		}
	}

	@Override
	public <T> T create(String typeName) {
		try {
			@SuppressWarnings("unchecked")
			Class<T> type = (Class<T>) Class.forName(typeName);
			return create(type);
		} catch (ClassNotFoundException e) {
			throw log.classNotFoundError(e, typeName);
		}
	}
}
