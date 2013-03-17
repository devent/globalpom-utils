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
package com.anrisoftware.globalpom.resources;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Converts a string path to a URI.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.2
 */
public class StringToURI {

	/**
	 * Converts the specified path to a URI.
	 * 
	 * @param path
	 *            the path.
	 * 
	 * @return the {@link URI}
	 * 
	 * @throws MalformedURLException
	 *             if the path is not a valid {@link URL}.
	 * 
	 * @throws URISyntaxException
	 *             if the path is not a valid {@link URL}.
	 */
	public URI convert(String path) throws MalformedURLException,
			URISyntaxException {
		URL url = new URL(path);
		String nullFragment = null;
		return new URI(url.getProtocol(), url.getHost(), url.getPath(),
				url.getQuery(), nullFragment);
	}

}
