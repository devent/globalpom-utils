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
package com.anrisoftware.globalpom.spreadsheetimport;

import java.util.List;

import javax.inject.Inject;
import javax.swing.event.TableModelListener;

import com.anrisoftware.globalpom.data.Data;
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
