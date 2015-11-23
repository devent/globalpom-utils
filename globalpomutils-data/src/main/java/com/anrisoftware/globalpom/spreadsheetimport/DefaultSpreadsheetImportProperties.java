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

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.File;
import java.io.Serializable;
import java.net.URI;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Default spreadsheet import properties.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.14
 */
@SuppressWarnings("serial")
public class DefaultSpreadsheetImportProperties implements
        SpreadsheetImportProperties, Serializable {

    private URI file;

    private int sheetNumber;

    private int[] columns;

    private int startRow;

    private int endRow;

    private boolean haveHeader;

    /**
     * @see DefaultSpreadsheetImportPropertiesFactory#create()
     */
    @AssistedInject
    public DefaultSpreadsheetImportProperties() {
        this.file = new File("").toURI();
        this.sheetNumber = 0;
        this.columns = new int[] {};
        this.startRow = 0;
        this.endRow = -1;
    }

    /**
     * @see DefaultSpreadsheetImportPropertiesFactory#create(SpreadsheetImportProperties)
     */
    @AssistedInject
    public DefaultSpreadsheetImportProperties(
            @Assisted SpreadsheetImportProperties p) {
        setFile(p.getFile());
        setSheetNumber(p.getSheetNumber());
        setColumns(p.getColumns());
        setStartRow(p.getStartRow());
        setEndRow(p.getEndRow());
        setHaveHeader(p.isHaveHeader());
    }

    public void setFile(URI file) {
        notNull(file, "file");
        this.file = file;
    }

    @Override
    public URI getFile() {
        return file;
    }

    public void setSheetNumber(int sheetNumber) {
        isTrue(sheetNumber > -1, "sheetNumber > -1");
        this.sheetNumber = sheetNumber;
    }

    @Override
    public int getSheetNumber() {
        return sheetNumber;
    }

    public void setColumns(int[] columns) {
        notNull(columns, "columns");
        this.columns = columns;
    }

    @Override
    public int[] getColumns() {
        return columns;
    }

    public void setStartRow(int startRow) {
        isTrue(startRow > -1, "startRow > -1");
        this.startRow = startRow;
    }

    @Override
    public int getStartRow() {
        return startRow;
    }

    public void setEndRow(int endRow) {
        isTrue(endRow == -1 || endRow >= startRow,
                "endRow(%d) >= startRow(%d)", endRow, startRow);
        this.endRow = endRow;
    }

    @Override
    public int getEndRow() {
        return endRow;
    }

    public void setHaveHeader(boolean haveHeader) {
        this.haveHeader = haveHeader;
    }

    @Override
    public boolean isHaveHeader() {
        return haveHeader;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
