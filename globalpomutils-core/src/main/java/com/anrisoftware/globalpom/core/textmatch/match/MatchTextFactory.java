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
package com.anrisoftware.globalpom.core.textmatch.match;

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
