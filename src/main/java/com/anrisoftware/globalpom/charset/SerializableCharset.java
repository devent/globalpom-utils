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
