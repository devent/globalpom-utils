/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-data.
 *
 * globalpomutils-data is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-data is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-data. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.csvimport;

import static com.anrisoftware.globalpom.csvimport.MatrixDataCsvImportLogger._.imported_data_debug;
import static com.anrisoftware.globalpom.csvimport.MatrixDataCsvImportLogger._.imported_data_trace;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link MatrixDataCsvImport}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
class MatrixDataCsvImportLogger extends AbstractLogger {

	enum _ {

		imported_data_debug(
				"Matrix data imported from '{}', {} columns, {} rows."),

		imported_data_trace("Matrix data imported from '{}' {}.");

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
	 * Creates a logger for {@link MatrixDataCsvImport}.
	 */
	public MatrixDataCsvImportLogger() {
		super(MatrixDataCsvImport.class);
	}

	void importedData(MatrixDataCsvImport data, CsvImporter importer) {
		if (isTraceEnabled()) {
			trace(imported_data_trace, importer.getProperties().getFile(),
					data);
		} else {
			debug(imported_data_debug, importer.getProperties().getFile(),
					data.getNumCols(), data.getNumRows());
		}
	}

}
