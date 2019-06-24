/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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
package com.anrisoftware.globalpom.core.resources;

import static java.lang.String.format;

import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * Converts a path to a URL.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class ToURL {

    /**
     * @see ToURL#convert(Object, String)
     *
     * @param path   the path.
     *
     * @param scheme the {@link String} the scheme.
     *
     * @return the {@link URL}
     *
     * @throws ConvertException if there were errors converting the path to the URL.
     */
    public static URL toURL(Object path, String scheme) throws ConvertException {
        return ResourcesModule.getToURLFactory().create().convert(path, scheme);
    }

    /**
     * @see ToURL#convert(Object)
     *
     * @param path the path.
     *
     * @return the {@link URL}
     *
     * @throws ConvertException if there were errors converting the path to the URL.
     */
    public static URL toURL(Object path) throws ConvertException {
        return ResourcesModule.getToURLFactory().create().convert(path);
    }

    /**
     * @see #convert(Object, String)
     *
     * @param path the path; can be of type {@link URL}, {@link URI}, {@link File},
     *             {@link String} or {@link Object}. If the path is not absolute the
     *             scheme {@code "file:"} will be added.
     *
     * @return the {@link URL}
     *
     * @throws ConvertException if there were errors converting the path to the URL.
     */
    public URL convert(Object path) throws ConvertException {
        return convert(path, "file:");
    }

    /**
     * Converts the specified path to a URL.
     *
     * @param path   the path; can be of type {@link URL}, {@link URI},
     *               {@link File}, {@link String} or {@link Object}. If the path is
     *               not absolute the scheme {@code "file:"} will be added.
     *
     * @param scheme the scheme of the URL if the path is not absolute.
     *
     * @return the {@link URL}.
     *
     * @throws ConvertException if there were errors converting the path to the URL.
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
            throw new ConvertException(e, uri);
        }
    }

    private URL toURL(File file) throws ConvertException {
        try {
            return file.toURI().toURL();
        } catch (Exception e) {
            throw new ConvertException(e, file);
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
            throw new ConvertException(e, path);
        }
    }

    private URL relativeToURL(String path, String scheme) {
        try {
            URI uri = new URI(format("%s%s", scheme, path));
            return uri.toURL();
        } catch (Exception e) {
            throw new ConvertException(e, path, scheme);
        }
    }
}
