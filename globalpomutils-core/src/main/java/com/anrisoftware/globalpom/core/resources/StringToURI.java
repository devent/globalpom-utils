/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static com.anrisoftware.globalpom.core.resources.ResourcesModule.getStringToURIFactory;

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
     *
     * @param path the {@link String} path.
     *
     * @return the {@link URI}
     */
    public static URI toURI(String path) throws ConvertException {
        return getStringToURIFactory().create().convert(path);
    }

    /**
     * @see StringToURI#convert(String, String)
     *
     * @param path     the {@link String} path.
     *
     * @param protocol the {@link String} protocol.
     *
     * @return the {@link URI}
     */
    public static URI toURI(String path, String protocol) throws ConvertException {
        return getStringToURIFactory().create().convert(path, protocol);
    }

    /**
     * Converts the specified path to a URI with the default protocol
     * {@code "file:".}
     *
     * @see #convert(String, String)
     *
     * @param path the {@link String} path.
     *
     * @return the {@link URI}
     */
    public URI convert(String path) throws ConvertException {
        return convert(path, "file:");
    }

    /**
     * Converts the specified path to a URI.
     *
     * @param path     the path.
     *
     * @param protocol the protocol of the path.
     *
     * @return the {@link URI}
     *
     * @throws ConvertException if the path is not a valid {@link URL}.
     */
    public URI convert(String path, String protocol) throws ConvertException {
        try {
            path = attachProtocol(path, protocol);
            return new URL(path).toURI();
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
