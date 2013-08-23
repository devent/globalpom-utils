package com.anrisoftware.globalpom.fileresourcemanager;

import java.io.File;

/**
 * Factory to create the resource saver.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
public interface ResourceSaverFactory {

	/**
	 * Creates the resource saver with the specified store directory.
	 * 
	 * @param storeDir
	 *            the store {@link File} directory.
	 * 
	 * @return the {@link ResourceSaver}.
	 */
	ResourceSaver create(File storeDir);
}
