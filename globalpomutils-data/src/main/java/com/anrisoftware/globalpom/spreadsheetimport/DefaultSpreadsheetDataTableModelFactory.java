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
package com.anrisoftware.globalpom.spreadsheetimport;

import java.util.List;

import com.anrisoftware.globalpom.data.Data;

/**
 * Factory to create the default spreadsheet data table model.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.14
 */
public interface DefaultSpreadsheetDataTableModelFactory {

    /**
     * Creates the default spreadsheet data table model.
     *
     * @param data
     *            the {@link Data}.
     *
     * @param columnNames
     *            the {@link List} of the column names.
     *
     * @return the {@link DefaultSpreadsheetDataTableModel}.
     */
    DefaultSpreadsheetDataTableModel create(Data data, List<String> columnNames);
}
