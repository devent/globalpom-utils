/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import static com.anrisoftware.globalpom.resources.ResourcesModule.getStringToURIFactory;

import java.net.URI;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Converts a string path to a URI.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.2
 */
public class StringToURI {

    private static final Pattern PROTOCOL = Pattern.compile(".*?://.*");

    /**
     * @see StringToURI#convert(String)
     */
    public static URI toURI(String path) throws ConvertException {
        return getStringToURIFactory().create().convert(path);
    }

    /**
     * @see StringToURI#convert(String, String)
     */
    public static URI toURI(String path, String protocol)
            throws ConvertException {
        return getStringToURIFactory().create().convert(path, protocol);
    }

    /**
     * Converts the specified path to a URI with the default protocol
     * {@code "file://".}
     *
     * @see #convert(String, String)
     */
    public URI convert(String path) throws ConvertException {
        return convert(path, "file://");
    }

    /**
     * Converts the specified path to a URI.
     *
     * @param path
     *            the path.
     *
     * @param protocol
     *            the protocol of the path.
     *
     * @return the {@link URI}
     *
     * @throws ConvertException
     *             if the path is not a valid {@link URL}.
     */
    public URI convert(String path, String protocol) throws ConvertException {
        try {
            path = attachProtocol(path, protocol);
            URL url = new URL(path);
            String nullFragment = null;
            return new URI(url.getProtocol(), url.getHost(), url.getPath(),
                    url.getQuery(), nullFragment);
        } catch (Exception e) {
            throw new ConvertException(e, path, protocol);
        }
    }

    private String attachProtocol(String path, String protocol) {
        if (!PROTOCOL.matcher(path).matches()) {
            return String.format("%s%s", protocol, path);
        }
        return path;
    }
}
