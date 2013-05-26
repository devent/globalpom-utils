package com.anrisoftware.globalpom.resources;

import static com.google.inject.Guice.createInjector;
import static java.lang.String.format;

import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.Injector;

/**
 * Converts a path to a URL.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
public class ToURL {

	private static final Injector INJECTOR = createInjector();

	/**
	 * @see ToURL#convert(Object, String)
	 */
	public static URL toURL(Object path, String scheme) throws ConvertException {
		return INJECTOR.getInstance(ToURL.class).convert(path, scheme);
	}

	/**
	 * @see ToURL#convert(Object)
	 */
	public static URL toURL(Object path) throws ConvertException {
		return INJECTOR.getInstance(ToURL.class).convert(path);
	}

	private final ToURLLogger log;

	@Inject
	ToURL(ToURLLogger logger) {
		this.log = logger;
	}

	/**
	 * @see #convert(Object, String)
	 * 
	 * @param path
	 *            the path; can be of type {@link URL}, {@link URI},
	 *            {@link File}, {@link String} or {@link Object}. If the path is
	 *            not absolute the scheme {@code "file://"} will be added.
	 */
	public URL convert(Object path) throws ConvertException {
		return convert(path, "file://");
	}

	/**
	 * Converts the specified path to a URL.
	 * 
	 * @param path
	 *            the path; can be of type {@link URL}, {@link URI},
	 *            {@link File}, {@link String} or {@link Object}. If the path is
	 *            not absolute the scheme {@code "file://"} will be added.
	 * 
	 * @param scheme
	 *            the scheme of the URL if the path is not absolute.
	 * 
	 * @return the {@link URL}.
	 * 
	 * @throws ConvertException
	 *             if there were errors converting the path to the URL.
	 */
	public URL convert(Object path, String scheme) throws ConvertException {
		if (path instanceof URL) {
			return (URL) path;
		}
		if (path instanceof URI) {
			return toURL(((URI) path));
		}
		if (path instanceof File) {
			return toURL((File) path);
		}
		return toURL(path.toString(), scheme);
	}

	private URL toURL(URI uri) throws ConvertException {
		try {
			return uri.toURL();
		} catch (Exception e) {
			throw log.errorConvert(e, uri);
		}
	}

	private URL toURL(File file) throws ConvertException {
		try {
			return file.toURI().toURL();
		} catch (Exception e) {
			throw log.errorConvert(e, file);
		}
	}

	private URL toURL(String path, String scheme) throws ConvertException {
		URL url = absoluteToURL(path);
		return url == null ? relativeToURL(path, scheme) : url;
	}

	private URL absoluteToURL(String path) {
		try {
			URI uri = new URI(path);
			if (uri.isAbsolute()) {
				return uri.toURL();
			} else {
				return null;
			}
		} catch (Exception e) {
			throw log.errorConvert(e, path);
		}
	}

	private URL relativeToURL(String path, String scheme) {
		try {
			URI uri = new URI(format("%s%s", scheme, path));
			return uri.toURL();
		} catch (Exception e) {
			throw log.errorConvert(e, path, scheme);
		}
	}
}
