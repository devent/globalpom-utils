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
