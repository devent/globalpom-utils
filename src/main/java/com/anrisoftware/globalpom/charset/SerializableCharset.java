/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.charset;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.charset.Charset;

/**
 * Serialize the specified character set.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.7
 */
public class SerializableCharset implements Externalizable {

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
	public final Charset getCharset() {
		return charset;
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
