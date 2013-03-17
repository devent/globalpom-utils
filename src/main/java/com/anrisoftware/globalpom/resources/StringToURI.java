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
