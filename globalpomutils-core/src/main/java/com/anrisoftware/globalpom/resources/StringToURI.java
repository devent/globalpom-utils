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

    private static final Pattern PROTOCOL = Pattern.compile("^.*?:.*");

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
