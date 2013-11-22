package com.anrisoftware.globalpom.constantsmap;

import com.anrisoftware.globalpom.format.constants.ConstantFormat;

/**
 * Factory to create the constants.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface ConstantsFactory {

	/**
	 * Creates the constants with the specified physical constants formatter.
	 * 
	 * @param format
	 *            the {@link ConstantFormat} physical constants formatter.
	 * 
	 * @return the {@link Constants}.
	 */
	Constants create(ConstantFormat format);
}
