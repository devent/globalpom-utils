package com.anrisoftware.globalpom.mnemonic;

/**
 * Factory for accelerator key.
 * 
 * @see Accelerator
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public interface AcceleratorFactory {

	/**
	 * Creates the accelerator from the specified string.
	 * 
	 * @param string
	 *            the {@link String}.
	 * 
	 * @return the {@link Accelerator}.
	 */
	Accelerator create(String string);
}
