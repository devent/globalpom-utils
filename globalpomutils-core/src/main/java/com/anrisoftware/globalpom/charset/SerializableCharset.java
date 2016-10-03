/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.charset;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Locale;
import java.util.Set;

/**
 * Serialize the specified character set.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.7
 */
public final class SerializableCharset implements Externalizable {

    /**
     * Decorates the character set for serialization.
     *
     * @param charset
     *            the {@link Charset}.
     *
     * @return the {@link SerializableCharset}.
     */
    public static SerializableCharset decorate(Charset charset) {
        return new SerializableCharset(charset);
    }

    /**
     * Decorates the character set for serialization.
     *
     * @param charset
     *            the {@link Charset}.
     *
     * @return the {@link SerializableCharset}.
     *
     * @since 2.0
     */
    public static SerializableCharset decorateSerializableCharset(
            Charset charset) {
        return new SerializableCharset(charset);
    }

    private Charset charset;

    /**
     * For serialization.
     */
    public SerializableCharset() {
    }

    private SerializableCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Returns the serialized character set.
     *
     * @return the {@link Charset}.
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * @see Charset#name()
     * @since 2.6
     */
    public String name() {
        return charset.name();
    }

    /**
     * @see Charset#aliases()
     * @since 2.6
     */
    public Set<String> aliases() {
        return charset.aliases();
    }

    /**
     * @see Charset#displayName()
     * @since 2.6
     */
    public String displayName() {
        return charset.displayName();
    }

    /**
     * @see Charset#isRegistered()
     * @since 2.6
     */
    public boolean isRegistered() {
        return charset.isRegistered();
    }

    /**
     * @see Charset#displayName(Locale)
     * @since 2.6
     */
    public String displayName(Locale locale) {
        return charset.displayName(locale);
    }

    /**
     * @see Charset#contains(Charset)
     * @since 2.6
     */
    public boolean contains(Charset cs) {
        return charset.contains(cs);
    }

    /**
     * @see Charset#newDecoder()
     * @since 2.6
     */
    public CharsetDecoder newDecoder() {
        return charset.newDecoder();
    }

    /**
     * @see Charset#newEncoder()
     * @since 2.6
     */
    public CharsetEncoder newEncoder() {
        return charset.newEncoder();
    }

    /**
     * @see Charset#canEncode()
     * @since 2.6
     */
    public boolean canEncode() {
        return charset.canEncode();
    }

    /**
     * @see Charset#decode(ByteBuffer)
     * @since 2.6
     */
    public CharBuffer decode(ByteBuffer bb) {
        return charset.decode(bb);
    }

    /**
     * @see Charset#encode(CharBuffer)
     * @since 2.6
     */
    public ByteBuffer encode(CharBuffer cb) {
        return charset.encode(cb);
    }

    /**
     * @see Charset#encode(String)
     * @since 2.6
     */
    public ByteBuffer encode(String str) {
        return charset.encode(str);
    }

    /**
     * @see Charset#compareTo(Charset)
     * @since 2.6
     */
    public int compareTo(Charset that) {
        return charset.compareTo(that);
    }

    /**
     * @see Charset#hashCode()
     * @since 2.6
     */
    @Override
    public int hashCode() {
        return charset.hashCode();
    }

    /**
     * @see Charset#equals(Object)
     * @since 2.6
     */
    @Override
    public boolean equals(Object ob) {
        return charset.equals(ob);
    }

    /**
     * @see Charset#toString()
     * @since 2.6
     */
    @Override
    public String toString() {
        return charset.toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(charset.name());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        String name = in.readUTF();
        charset = Charset.forName(name);
    }

}
