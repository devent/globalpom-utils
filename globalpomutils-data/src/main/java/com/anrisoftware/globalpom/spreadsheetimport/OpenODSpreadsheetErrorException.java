package com.anrisoftware.globalpom.spreadsheetimport;

import java.io.IOException;
import java.net.URI;

@SuppressWarnings("serial")
public class OpenODSpreadsheetErrorException extends SpreadsheetImportException {

    public OpenODSpreadsheetErrorException(IOException e, URI file) {
        super("Error open ODS file", e);
        addContextValue("file", file);
    }

}
