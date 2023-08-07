/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.projects.appresources;

import static java.util.Arrays.asList;

import java.util.Collection;

import jakarta.inject.Singleton;

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
