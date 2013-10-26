package com.anrisoftware.globalpom.data;

import static com.anrisoftware.globalpom.data.MatrixDataCsvImportLogger._.imported_data_debug;
import static com.anrisoftware.globalpom.data.MatrixDataCsvImportLogger._.imported_data_trace;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.dataimport.CsvImporter;
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
