/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.data.csvimport;

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
