package com.anrisoftware.globalpom.mnemonic;

/**
 * Factory for mnemonic.
 * 
 * @see Mnemonic
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public interface MnemonicFactory {

	/**
	 * Creates the mnemonic from the specified string.
	 * 
	 * @param string
	 *            the {@link String}.
	 * 
	 * @return the {@link Mnemonic}.
	 */
	Mnemonic create(String string);
}
