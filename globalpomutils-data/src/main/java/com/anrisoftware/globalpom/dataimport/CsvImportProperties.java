/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.dataimport;

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
