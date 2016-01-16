/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-projects.
 *
 * globalpomutils-projects is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-projects is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-projects. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.projects.appresources;

import static java.util.Arrays.asList;

import java.util.Collection;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;
import com.google.inject.CreationException;
import com.google.inject.spi.Message;

/**
 * Logging messages for {@link AbstractAppResourcesProvider}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class AbstractAppResourcesProviderLogger extends AbstractLogger {

	/**
	 * Creates a logger for {@link AbstractAppResourcesProvider}.
	 */
	public AbstractAppResourcesProviderLogger() {
		super(AbstractAppResourcesProvider.class);
	}

	CreationException errorLoadResources(Exception e, Object source) {
		Collection<Message> messages = asList(new Message(asList(source),
				"Error load application resources", e));
		return logException(new CreationException(messages),
				"Error load application resources");
	}

}
