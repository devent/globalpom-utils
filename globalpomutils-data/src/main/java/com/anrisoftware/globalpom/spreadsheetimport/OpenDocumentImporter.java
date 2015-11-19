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

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SheetTableModel;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.anrisoftware.globalpom.dataimport.Column;
import com.google.inject.assistedinject.Assisted;

/**
 * Imports Open Document spreadsheet.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.14
 */
public class OpenDocumentImporter implements SpreadsheetImporter {

    private final DefaultSpreadsheetImportProperties properties;

    @Inject
    private OpenDocumentImporterLogger log;

    private Sheet sheet;

    private int currentRow;

    private SheetTableModel<SpreadSheet> tableModel;

    private List<String> headers;

    private int[] columns;

    private List<Cell<?>> currentCells;

    /**
     * @see OpenDocumentImporterFactory#create(SpreadsheetImportProperties)
     */
    @Inject
    OpenDocumentImporter(
            DefaultSpreadsheetImportPropertiesFactory propertiesFactory,
            @Assisted SpreadsheetImportProperties properties) {
        this.properties = propertiesFactory.create(properties);
    }

    @Override
    public SpreadsheetImporter open() throws SpreadsheetImportException {
        try {
            loadSpreadsheet();
            return this;
        } catch (IOException e) {
            throw new OpenODSpreadsheetErrorException(e, properties.getFile());
        }
    }

    @Override
    public boolean loadNext() throws SpreadsheetImportException {
        if (currentRow < tableModel.getRowCount()) {
            currentCells.clear();
            Cell<SpreadSheet> cell;
            for (int i = 0; i < columns.length; i++) {
                cell = tableModel.getImmutableCellAt(currentRow, columns[i]);
                currentCells.add(cell);
            }
            currentRow++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public SpreadsheetImportProperties getProperties() {
        return properties;
    }

    @Override
    public List<String> getHeaders() {
        return headers;
    }

    @Override
    public List<String> getValues() {
        List<String> values = new ArrayList<String>(currentCells.size());
        for (int i = 0; i < currentCells.size(); i++) {
            values.add(currentCells.get(i).getTextValue());
        }
        return Collections.unmodifiableList(values);
    }

    @Override
    public Map<String, Object> mapValues(List<Column> columns)
            throws ParseException {
        List<Cell<?>> values = this.currentCells;
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < values.size(); i++) {
            Cell<?> cell = values.get(i);
            Object parsed = columns.get(i).parseValue(cell.getTextValue());
            String name = columns.get(i).getName();
            map.put(name, parsed);
        }
        return map;
    }

    @Override
    public Map<String, Object> mapValues(Map<String, Column> columns,
            List<String> columnNames) throws ParseException {
        List<Cell<?>> values = this.currentCells;
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < values.size(); i++) {
            String name = columnNames.get(i);
            Column column = columns.get(name);
            log.checkColumnForName(column, name);
            Object parsed = column.parseValue(values.get(i).getTextValue());
            map.put(name, parsed);
        }
        return map;
    }

    private void loadSpreadsheet() throws IOException {
        File file = new File(properties.getFile());
        SpreadSheet spreadsheet = SpreadSheet.createFromFile(file);
        this.sheet = spreadsheet.getSheet(properties.getSheetNumber());
        this.columns = getColumns();
        int firstColumn = columns[0];
        int firstRow = properties.getStartRow();
        int lastCol = columns[columns.length - 1] + 1;
        this.properties.setEndRow(sheet.getRowCount() - 1);
        int lastRow = properties.getEndRow() + 1;
        this.tableModel = sheet.getTableModel(firstColumn, firstRow, lastCol,
                lastRow);
        this.currentRow = properties.getStartRow();
        this.currentCells = new ArrayList<Cell<?>>(columns.length);
        loadHeaderRow();
    }

    private void loadHeaderRow() {
        if (!properties.isHaveHeader()) {
            return;
        }
        List<String> headers = new ArrayList<String>();
        Cell<SpreadSheet> cell;
        for (int i = 0; i < columns.length; i++) {
            cell = tableModel.getImmutableCellAt(0, columns[i]);
            headers.add(cell.getTextValue());
        }
        currentRow++;
        this.headers = Collections.unmodifiableList(headers);
    }

    private int[] getColumns() {
        int[] columns = properties.getColumns();
        if (columns.length == 0) {
            columns = new int[sheet.getColumnCount()];
            for (int i = 0; i < columns.length; i++) {
                columns[i] = i;
            }
        }
        Arrays.sort(columns);
        return columns;
    }

}
