/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
public class DefaultSpreadsheetImportProperties implements SpreadsheetImportProperties, Serializable {

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
     *
     * @param p the {@link SpreadsheetImportProperties}
     */
    @AssistedInject
    public DefaultSpreadsheetImportProperties(@Assisted SpreadsheetImportProperties p) {
        setFile(p.getFile());
        setSheetNumber(p.getSheetNumber());
        setColumns(p.getColumns());
        setStartRow(p.getStartRow());
        setEndRow(p.getEndRow());
        setHaveHeader(p.isHaveHeader());
    }

    /**
     *
     * @param file the {@link URI}
     */
    public void setFile(URI file) {
        notNull(file, "file");
        this.file = file;
    }

    @Override
    public URI getFile() {
        return file;
    }

    /**
     *
     * @param sheetNumber the sheet number.
     */
    public void setSheetNumber(int sheetNumber) {
        isTrue(sheetNumber > -1, "sheetNumber > -1");
        this.sheetNumber = sheetNumber;
    }

    @Override
    public int getSheetNumber() {
        return sheetNumber;
    }

    /**
     * @param columns the columns.
     */
    public void setColumns(int[] columns) {
        notNull(columns, "columns");
        this.columns = columns;
    }

    @Override
    public int[] getColumns() {
        return columns;
    }

    /**
     *
     * @param startRow the start row.
     */
    public void setStartRow(int startRow) {
        isTrue(startRow > -1, "startRow > -1");
        this.startRow = startRow;
    }

    @Override
    public int getStartRow() {
        return startRow;
    }

    /**
     *
     * @param endRow the end row.
     */
    public void setEndRow(int endRow) {
        isTrue(endRow == -1 || endRow >= startRow, "endRow(%d) >= startRow(%d)", endRow, startRow);
        this.endRow = endRow;
    }

    @Override
    public int getEndRow() {
        return endRow;
    }

    /**
     *
     * @param haveHeader the have headers.
     */
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
