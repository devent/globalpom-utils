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
package com.anrisoftware.globalpom.dataimport;

/**
 * Factory to create default CSV importer properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface DefaultCsvImportPropertiesFactory {

	/**
	 * Creates the default CSV importer properties.
	 * 
	 * @return the {@link DefaultCsvImportProperties}.
	 */
	DefaultCsvImportProperties create();

	/**
	 * Creates the default CSV importer properties which values are copied from
	 * the specified properties.
	 * 
	 * @param properties
	 *            the {@link CsvImportProperties}.
	 * 
	 * @return the {@link DefaultCsvImportProperties}.
	 */
	DefaultCsvImportProperties create(CsvImportProperties properties);
}
