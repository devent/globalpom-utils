/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Input stream that reads from a byte buffer. From <a href=
 * "http://stackoverflow.com/questions/4332264/wrapping-a-bytebuffer-with-an-inputstream"
 * >Wrapping a ByteBuffer with an InputStream [stackoverflow.com]</a>
 *
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.16
 */
public class ByteBufferBackedInputStream extends InputStream {

    private final ByteBuffer buf;

    public ByteBufferBackedInputStream(ByteBuffer buf) {
        this.buf = buf;
    }

    @Override
    public int read() throws IOException {
        if (!buf.hasRemaining()) {
            return -1;
        }
        return buf.get() & 0xFF;
    }

    @Override
    public int read(byte[] bytes, int off, int len) throws IOException {
        if (!buf.hasRemaining()) {
            return -1;
        }
        len = Math.min(len, buf.remaining());
        buf.get(bytes, off, len);
        return len;
    }
}
