/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

/*-
 * #%L
 * Global POM Utilities :: Data
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.text.Format;

/**
 * Factory to import data from CSV file importer.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface MatrixDataCsvImportFactory {

    /**
     * Imports data from the specified importer.
     * 
     * @param importer
     *            the {@link CsvImporter}.
     * 
     * @return the {@link MatrixDataCsvImport}.
     */
    MatrixDataCsvImport create(CsvImporter importer);

    /**
     * Imports data from the specified importer.
     * 
     * @param matrix
     *            the {@link CsvImporter}
     * 
     * @param format
     *            the {@link Format} to parse the text to double values.
     * 
     * @return the {@link MatrixDataCsvImport}.
     * 
     * @since 1.11
     */
    MatrixDataCsvImport create(CsvImporter matrix, Format format);

}
