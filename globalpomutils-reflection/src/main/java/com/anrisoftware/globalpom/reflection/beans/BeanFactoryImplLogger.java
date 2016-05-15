/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-reflection.
 *
 * globalpomutils-reflection is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-reflection is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-reflection. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.reflection.beans;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link BeanAccessImpl}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanFactoryImplLogger extends AbstractLogger {

	enum _ {

		name_("name"),

		find_message("Cannot find the class '{}'."),

		find("Cannot find the class"),

		instantiate_message("Can not instantiate {}."),

		instantiate("Can not instantiate"),

		no_standard_message("No standard constructor found for {}."),

		no_standard("No standard constructor found"),

		exception_thrown_message(
				"Exception thrown in the standard constructor of {}."),

		exception_thrown("Exception thrown in the standard constructor"),

		type("type"),

		illegal_access("Illegal access to the standard constructor"),

		illegal_access_message(
				"Illegal access to the standard constructor of {}.");

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
	 * Creates logger for {@link BeanAccessImpl}.
	 */
	BeanFactoryImplLogger() {
		super(BeanAccessImpl.class);
	}

}
