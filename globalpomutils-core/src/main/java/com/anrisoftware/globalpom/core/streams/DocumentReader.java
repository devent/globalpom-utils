/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.streams;

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
