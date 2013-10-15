/*
 * Copyright 2013-2013 Erwin Müller <erwin.mueller@deventm.org>
 * 
 * This file is part of prefdialog-csvimportdialog.
 * 
 * prefdialog-csvimportdialog is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * prefdialog-csvimportdialog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with prefdialog-csvimportdialog. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.dataimport;

import static com.anrisoftware.globalpom.dataimport.CsvImporterImplLogger._.error_open_file;
import static com.anrisoftware.globalpom.dataimport.CsvImporterImplLogger._.error_open_file_message;
import static com.anrisoftware.globalpom.dataimport.CsvImporterImplLogger._.error_read;
import static com.anrisoftware.globalpom.dataimport.CsvImporterImplLogger._.error_read_message;

import java.io.IOException;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link CsvImporterImpl}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
class CsvImporterImplLogger extends AbstractLogger {

	enum _ {

		importer("importer"),

		error_open_file_message("Error open file '{}'."),

		error_open_file("Error open file"),

		error_read("Error read file"),

		error_read_message("Error read file '{}'.");

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
	 * Creates a logger for {@link CsvImporterImpl}.
	 */
	public CsvImporterImplLogger() {
		super(CsvImporterImpl.class);
	}

	CsvImportException errorOpenFile(CsvImporterImpl importer, Exception e) {
		return logException(new CsvImportException(error_open_file, e).add(
				importer, importer), error_open_file_message, importer
				.getProperties().getFile());
	}

	CsvImportException errorRead(CsvImporterImpl importer, IOException e) {
		return logException(
				new CsvImportException(error_read, e).add(importer, importer),
				error_read_message, importer.getProperties().getFile());
	}

}
