/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-fileresources.
 *
 * globalpomutils-fileresources is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-fileresources is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-fileresources. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.fileresourcemanager;

import java.io.File;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link ResourceSaver}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class ResourceSaverLogger extends AbstractLogger {

	private enum _ {

		STORE_DIRECTORY("directory"),

		ID("transaction ID"),

		RESOURCES("resources"),

		RESOURCE("resource"),

		ERROR_START_MANAGER1(
				"Error start file resource manager in store directory '{}'."),

		ERROR_START_MANAGER("Error start file resource manager"),

		ERROR_START_TRANSACTION("Error start transaction"),

		ERROR_START_TRANSACTION1(
				"Error start transaction in store directory '{}'."),

		ERROR_ROLLBACK_TRANSACTION("Error rollback transaction"),

		ERROR_ROLLBACK_TRANSACTION1("Error rollback transaction ID {}."),

		ERROR_STOP_MANAGER1(
				"Error stop file resource manager in store directory '{}'."),

		ERROR_STOP_MANAGER("Error stop file resource manager"),

		ERROR_SAVE_RESOURCES1("Error save resources in store directory '{}'."),

		ERROR_SAVE_RESOURCES("Error save resources"),

		ERROR_SAVE_RESOURCE1(
				"Error save resource '{}' in store directory '{}'."),

		ERROR_SAVE_RESOURCE("Error save resource");

		private String name;

		private _(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	/**
	 * Creates a logger for {@link ResourceSaver}.
	 */
	public ResourceSaverLogger() {
		super(ResourceSaver.class);
	}

	FileResourceException errorStartManager(Exception e, File dir) {
		return logException(
				new FileResourceException(_.ERROR_START_MANAGER, e).add(
						_.STORE_DIRECTORY, dir), _.ERROR_START_MANAGER1, dir);
	}

	FileResourceException errorStartTransaction(Exception e, File dir) {
		return logException(new FileResourceException(
				_.ERROR_START_TRANSACTION, e).add(_.STORE_DIRECTORY, dir),
				_.ERROR_START_TRANSACTION1, dir);
	}

	FileResourceException errorRollbackTransaction(Exception e, String id) {
		return logException(new FileResourceException(
				_.ERROR_ROLLBACK_TRANSACTION, e).add(_.ID, id),
				_.ERROR_ROLLBACK_TRANSACTION1, id);
	}

	FileResourceException errorStopManager(Exception e, String dir) {
		return logException(
				new FileResourceException(_.ERROR_STOP_MANAGER, e).add(
						_.STORE_DIRECTORY, dir), _.ERROR_STOP_MANAGER1, dir);
	}

	FileResourceException errorSave(Throwable e, Resource[] resources, File dir) {
		return logException(
				new FileResourceException(_.ERROR_SAVE_RESOURCES, e).add(
						_.RESOURCES, resources).add(_.STORE_DIRECTORY, dir),
				_.ERROR_SAVE_RESOURCES1, dir);
	}

	public FileResourceException errorSaveResource(Exception e,
			Resource resource, File dir) {
		return logException(new FileResourceException(_.ERROR_SAVE_RESOURCE, e)
				.add(_.RESOURCE, resource).add(_.STORE_DIRECTORY, dir),
				_.ERROR_SAVE_RESOURCE1, resource.getName(), dir);
	}
}
