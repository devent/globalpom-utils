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
package com.anrisoftware.globalpom.textmatch.match;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * Factory to create a text matcher that compares the content of a text file or
 * resource to a given pattern.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public interface MatchTextFactory {

    /**
     * Creates a new worker that compares the content of a text file to a given
     * pattern.
     * 
     * @param file
     *            the {@link File} which content is read.
     * 
     * @param pattern
     *            the comparison {@link Pattern}.
     * 
     * @param charset
     *            the {@link Charset} of the text file.
     * 
     * @return the {@link MatchText}.
     */
    MatchText create(File file, Pattern pattern, Charset charset);

    /**
     * Creates a new worker that compares the content of a text resource to a
     * given pattern.
     * 
     * @param resource
     *            the {@link URL} of the resource which content is read.
     * 
     * @param pattern
     *            the comparison {@link Pattern}.
     * 
     * @param charset
     *            the {@link Charset} of the text file.
     * 
     * @return the {@link MatchText}.
     * 
     * @throws URISyntaxException
     *             if the resource is not a valid URI.
     */
    MatchText create(URL resource, Pattern pattern, Charset charset)
            throws URISyntaxException;

    /**
     * Creates a new worker that compares the content of a text resource to a
     * given pattern.
     * 
     * @param resource
     *            the {@link URI} of the resource which content is read.
     * 
     * @param pattern
     *            the comparison {@link Pattern}.
     * 
     * @param charset
     *            the {@link Charset} of the text file.
     * 
     * @return the {@link MatchText}.
     */
    MatchText create(URI resource, Pattern pattern, Charset charset);
}
