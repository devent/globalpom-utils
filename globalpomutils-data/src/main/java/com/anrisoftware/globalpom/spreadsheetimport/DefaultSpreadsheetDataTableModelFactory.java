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
