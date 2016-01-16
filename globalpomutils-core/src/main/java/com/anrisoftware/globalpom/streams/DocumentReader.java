/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.streams;

import java.io.IOException;
import java.io.Reader;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.commons.math3.util.FastMath;

public class DocumentReader extends Reader {

    private final Document document;

    private int documentOffset;

    public DocumentReader(Document document) {
        super();
        this.documentOffset = 0;
        this.document = document;
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int offset = documentOffset;
        int dlen = document.getLength() - offset;
        if (dlen <= 0) {
            return -1;
        }
        try {
            String text = document.getText(offset, FastMath.min(len, dlen));
            int textlen = text.length();
            System.arraycopy(text.toCharArray(), 0, cbuf, off, textlen);
            this.documentOffset += textlen;
            return textlen;
        } catch (BadLocationException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        this.documentOffset = 0;
    }

}
