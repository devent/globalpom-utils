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

/*-
 * #%L
 * Global POM Utilities :: Core
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static java.lang.String.format;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

/**
 * Converts a path to a URI.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class ToURI {

    /**
     * @param path
     *            the path; can be of type {@link URL}, {@link URI},
     *            {@link File}, {@link String} or {@link Object}. If the path is
     *            not absolute the scheme {@code "file:"} will be added.
     *
     * @since 3.2
     */
    public static ToURI toURI(Object path) throws ConvertException {
        return ResourcesModule.getToURIFactory().create(path);
    }

    private final Object path;

    private String scheme;

    private boolean scp;

    @Inject
    ToURI(@Assisted Object path) {
        this.scheme = "file:";
        this.path = path;
        this.scp = false;
    }

    /**
     * Sets the default scheme.
     *
     * @since 3.2
     */
    public ToURI withScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    /**
     * Sets to parse SCP syntax.
     *
     * @since 3.2
     */
    public ToURI withScp() {
        this.scp = true;
        return this;
    }

    /**
     * Converts the path to a URI.
     *
     * @since 3.2
     */
    public URI convert() throws ConvertException {
        if (path instanceof URL) {
            return urltoURI((URL) path);
        }
        if (path instanceof URI) {
            return (URI) path;
        }
        if (path instanceof File) {
            return ((File) path).toURI();
        }
        return toURI(path.toString());
    }

    private URI urltoURI(URL path) {
        try {
            return path.toURI();
        } catch (URISyntaxException e) {
            throw new ConvertException(e, path);
        }
    }

    private URI toURI(String path) throws ConvertException {
        if (scp) {
            Matcher scpShortSyntax = isScpShortSyntax(path);
            if (scpShortSyntax.matches()) {
                return scpToURI(scpShortSyntax, path);
            }
        }
        URI uri = absoluteToURL(path);
        return uri == null ? relativeToURL(path) : uri;
    }

    private URI scpToURI(Matcher matcher, String path) {
        String user = matcher.group("user");
        String host = matcher.group("host");
        String dir = matcher.group("dir");
        if (dir.charAt(0) == '/') {
            dir = dir.substring(1);
        }
        String s;
        if (user != null) {
            s = String.format("ssh://%s@%s/%s", user, host, dir);
        } else {
            s = String.format("ssh://%s/%s", host, dir);
        }
        try {
            return new URI(s);
        } catch (URISyntaxException e) {
            throw new ConvertException(e, s);
        }
    }

    private final Pattern SCP_PATTERN = Pattern.compile(
            "(?<schema>.*:\\/\\/)?(:?(?<user>.*)@)?(?<host>(?:(?:(?:(?:[a-zA-Z0-9][-a-zA-Z0-9]{0,61})?[a-zA-Z0-9])[.])*(?:[a-zA-Z][-a-zA-Z0-9]{0,61}[a-zA-Z0-9]|[a-zA-Z])[.]?)):(?!\\/)(?<dir>.*)");

    private Matcher isScpShortSyntax(String path) {
        return SCP_PATTERN.matcher(path);
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

    private URI relativeToURL(String path) {
        try {
            return new URI(format("%s%s", scheme, path));
        } catch (Exception e) {
            throw new ConvertException(e, path, scheme);
        }
    }
}
