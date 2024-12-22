/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Properties to import a comma separated values file.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface CsvImportProperties {

    /**
     * Returns the path of the comma separated values file.
     *
     * @return the {@link File} path.
     */
    URI getFile();

    /**
     * Returns that the CSV data have a header row that determines the columns.
     *
     * @return {@code true} if the data have a header row.
     *
     * @since 2.3
     */
    boolean isHaveHeader();

    /**
     * Returns the character set of the file.
     *
     * @return the {@link Charset}.
     */
    Charset getCharset();

    /**
     * Returns the locale of the file. The locale determines the format of
     * numbers and dates.
     *
     * @return the {@link Locale}.
     */
    Locale getLocale();

    /**
     * Returns the separator character.
     *
     * @return the separator character.
     */
    char getSeparator();

    /**
     * Returns the text quote character.
     *
     * @return the text quote character
     */
    char getQuote();

    /**
     * Returns the symbols for a new line.
     *
     * @return new line symbols {@link String}.
     */
    String getEndOfLineSymbols();

    /**
     * Returns the start row. Data from the file is read beginning with the
     * start row.
     *
     * @return the start row.
     */
    int getStartRow();

    /**
     * Returns the number of columns of the data.
     *
     * @return the number of columns.
     */
    int getNumCols();

}
