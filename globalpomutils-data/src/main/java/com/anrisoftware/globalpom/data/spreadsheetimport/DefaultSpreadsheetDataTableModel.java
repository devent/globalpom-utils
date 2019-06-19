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
package com.anrisoftware.globalpom.data.spreadsheetimport;

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

import java.util.List;

import javax.inject.Inject;
import javax.swing.event.TableModelListener;

import com.anrisoftware.globalpom.data.data.Data;
import com.google.inject.assistedinject.Assisted;

/**
 * Makes the data available as a table model.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.14
 */
public class DefaultSpreadsheetDataTableModel implements
        SpreadsheetDataTableModel {

    private final Data data;

    private final List<String> columnNames;

    /**
     * @see DefaultSpreadsheetDataTableModelFactory#create(Data)
     */
    @Inject
    DefaultSpreadsheetDataTableModel(@Assisted Data data,
            @Assisted List<String> columnNames) {
        this.data = data;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return data.getNumRows();
    }

    @Override
    public int getColumnCount() {
        return data.getNumCols();
    }

    @Override
    public String getColumnName(int columnIndex) {
        int size = columnNames.size();
        if (size == 0) {
            return null;
        } else if (columnIndex >= size) {
            return columnNames.get(size - 1);
        } else {
            return columnNames.get(columnIndex);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Double.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex, columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

}
