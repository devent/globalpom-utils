/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.resources;

import static java.lang.String.format;

import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.inject.Inject;

/**
 * Converts a path to a URL.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class ToURL {

    /**
     * @see ToURL#convert(Object, String)
     */
    public static URL toURL(Object path, String scheme) throws ConvertException {
        return ResourcesModule.getToURLFactory().create().convert(path, scheme);
    }

    /**
     * @see ToURL#convert(Object)
     */
    public static URL toURL(Object path) throws ConvertException {
        return ResourcesModule.getToURLFactory().create().convert(path);
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
