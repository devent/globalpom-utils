/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.resources;

import static java.lang.String.format;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Converts a path to a URI.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class ToURI {

    /**
     * @see ToURI#convert(Object, String)
     */
    public static URI toURI(Object path, String scheme)
            throws ConvertException {
        return ResourcesModule.getToURIFactory().create().convert(path, scheme);
    }

    /**
     * @see ToURI#convert(Object)
     */
    public static URI toURI(Object path) throws ConvertException {
        return ResourcesModule.getToURIFactory().create().convert(path);
    }

    /**
     * @see #convert(Object, String)
     *
     * @param path
     *            the path; can be of type {@link URL}, {@link URI},
     *            {@link File}, {@link String} or {@link Object}. If the path is
     *            not absolute the scheme {@code "file:"} will be added.
     */
    public URI convert(Object path) throws ConvertException {
        return convert(path, "file:");
    }

    /**
     * Converts the specified path to a URI.
     *
     * @param path
     *            the path; can be of type {@link URL}, {@link URI},
     *            {@link File}, {@link String} or {@link Object}. If the path is
     *            not absolute the specified scheme will be added.
     *
     * @param scheme
     *            the scheme of the URL if the path is not absolute.
     *
     * @return the {@link URI}.
     *
     * @throws ConvertException
     *             if there were errors converting the path to the URL.
     */
    public URI convert(Object path, String scheme) throws ConvertException {
        if (path instanceof URL) {
            return urltoURI((URL) path);
        }
        if (path instanceof URI) {
            return (URI) path;
        }
        if (path instanceof File) {
            return ((File) path).toURI();
        }
        return toURI(path.toString(), scheme);
    }

    private URI urltoURI(URL path) {
        try {
            return path.toURI();
        } catch (URISyntaxException e) {
            throw new ConvertException(e, path);
        }
    }

    private URI toURI(String path, String scheme) throws ConvertException {
        URI uri = absoluteToURL(path);
        return uri == null ? relativeToURL(path, scheme) : uri;
    }

    private URI absoluteToURL(String path) {
        try {
            URI uri = new URI(path);
            if (uri.isAbsolute()) {
                return uri;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ConvertException(e, path);
        }
    }

    private URI relativeToURL(String path, String scheme) {
        try {
            return new URI(format("%s%s", scheme, path));
        } catch (Exception e) {
            throw new ConvertException(e, path, scheme);
        }
    }
}
