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

import javax.inject.Inject;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.google.inject.assistedinject.Assisted;

/**
 * Exports data to Open Document spreadsheet.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.14
 */
public class OpenDocumentExporter implements SpreadsheetExporter {

    private final DefaultSpreadsheetImportProperties properties;

    private final SpreadsheetDataTableModel model;

    /**
     * @see OpenDocumentExporterFactory#create(SpreadsheetImportProperties)
     */
    @Inject
    OpenDocumentExporter(
            DefaultSpreadsheetImportPropertiesFactory propertiesFactory,
            @Assisted SpreadsheetDataTableModel model,
            @Assisted SpreadsheetImportProperties properties) {
        this.model = model;
        this.properties = propertiesFactory.create(properties);
    }

    @Override
    public DefaultSpreadsheetImportProperties getProperties() {
        return properties;
    }

    @Override
    public SpreadsheetExporter write(File file)
            throws SpreadsheetExportException {
        try {
            SpreadSheet.createEmpty(model).saveAs(file);
        } catch (IOException e) {
            throw new WriteOpenDocumentException(e, file);
        }
        return this;
    }
}
