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

import static com.anrisoftware.globalpom.core.charset.SerializableCharset.decorateSerializableCharset;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.core.charset.SerializableCharset;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Compare the content of a text file to a given pattern.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@SuppressWarnings("serial")
public class MatchText implements Serializable {

    private static final String CHARACTER_SET = "character set";

    private static final String PATTERN = "pattern";

    private final URI resource;

    private final Pattern pattern;

    private final SerializableCharset charset;

    @Inject
    private MatchTextWorkerLogger log;

    private transient boolean matches;

    /**
     * @see MatchTextFactory#create(File, Pattern, Charset)
     */
    @AssistedInject
    MatchText(@Assisted File file, @Assisted Pattern pattern,
            @Assisted Charset charset) {
        this(file.toURI(), pattern, charset);
    }

    /**
     * @see MatchTextFactory#create(URL, Pattern, Charset)
     */
    @AssistedInject
    MatchText(@Assisted URL resource, @Assisted Pattern pattern,
            @Assisted Charset charset) throws URISyntaxException {
        this(resource.toURI(), pattern, charset);
    }

    /**
     * @see MatchTextFactory#create(URI, Pattern, Charset)
     */
    @AssistedInject
    MatchText(@Assisted URI resource, @Assisted Pattern pattern,
            @Assisted Charset charset) {
        this.resource = resource;
        this.pattern = pattern;
        this.charset = decorateSerializableCharset(charset);
    }

    /**
     * Matches the resource text and replaces the found text.
     *
     * @return this {@link MatchText}.
     *
     * @throws MatchTextException
     *             if there was an error reading the resource.
     */
    public MatchText match() throws MatchTextException {
        String string = readFile();
        compareText(string);
        return this;
    }

    private void compareText(String string) {
        matches = pattern.matcher(string).find();
        if (matches) {
            log.textWasFound(this);
        } else {
            log.textWasNotFound(this);
        }
    }

    private String readFile() throws MatchTextException {
        try {
            Charset encoding = charset.getCharset();
            InputStream input = resource.toURL().openStream();
            return IOUtils.toString(input, encoding);
        } catch (IOException e) {
            throw new ReadResourceException(e, resource, charset.getCharset());
        }
    }

    /**
     * Returns if the comparison pattern matches or not.
     *
     * @return {@code true} if it's matching, {@code false} if not.
     */
    public boolean isMatches() {
        return matches;
    }

    /**
     * Returns the text resource in which the text is compared to.
     *
     * @return the {@link URI}.
     */
    public URI getResource() {
        return resource;
    }

    /**
     * Returns the comparison pattern.
     *
     * @return the comparison {@link Pattern}.
     */
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(resource)
                .append(PATTERN, pattern).append(CHARACTER_SET, charset)
                .toString();
    }
}
