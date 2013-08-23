package com.anrisoftware.globalpom.fileresourcemanager;

import java.io.OutputStream;

/**
 * Resource that is saved.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
public interface Resource {

	/**
	 * Returns the name of the resource.
	 * 
	 * @return the {@link String} name.
	 */
	String getName();

	/**
	 * Saves the resource in the specified stream.
	 * 
	 * @param stream
	 *            the {@link OutputStream}.
	 * 
	 * @throws Exception
	 *             if some error occurs while saving the resource in the stream.
	 */
	void save(OutputStream stream) throws Exception;

}
