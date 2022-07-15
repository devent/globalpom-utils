/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.textmatch.tokentemplate;

import java.io.Serializable;
import java.util.regex.Matcher;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.inject.assistedinject.Assisted;

/**
 * Replace search text with a replacement. The replacement will be surrounded by
 * a begin and end token. If the search text can not be found the replacement
 * will be appended.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TokensTemplate implements Serializable {

    private static final String END_TOKEN = "end token";

    private static final String BEGIN_TOKEN = "begin token";

    private final String beginToken;

    private final String endToken;

    private final TokenTemplate template;

    private final String text;

    @Inject
    private TokensTemplateLogger log;

    private transient String replacement;

    /**
     * Sets the begin and end tokens, the template and the argument.
     *
     * @param tokenMarker the {@link TokenMarker} holding the begin and end tokens.
     *
     * @param template    the {@link TokenTemplate} containing the search text and
     *                    the replacement.
     *
     * @param text        the text in which to replace.
     */
    @Inject
    TokensTemplate(@Assisted TokenMarker tokenMarker, @Assisted TokenTemplate template, @Assisted String text) {
        this.beginToken = tokenMarker.getBeginToken();
        this.endToken = tokenMarker.getEndToken();
        this.template = template;
        this.text = text;
    }

    /**
     * Replace search text with a replacement.
     *
     * @return this {@link TokensTemplate}.
     */
    public TokensTemplate replace() {
        Matcher matcher;
        matcher = template.toPattern(beginToken, endToken).matcher(text);
        boolean find = matcher.find();
        String replace = template.toReplace(beginToken, endToken);
        if (find) {
            replace(matcher, replace);
        } else if (template.isAppend()) {
            appendReplace(replace);
        } else {
            replacement = text;
        }
        return this;
    }

    private void appendReplace(String replace) {
        replacement = text + replace;
        log.appendArgument(this);
    }

    private void replace(Matcher matcher, String replace) {
        if (template.isEscape()) {
            replace = escapeReplace(replace);
        }
        replacement = matcher.replaceFirst(replace);
        log.replacedArgument(this);
    }

    private String escapeReplace(String replace) {
        return Matcher.quoteReplacement(replace);
    }

    /**
     * Returns the formatted text.
     *
     * @return the text.
     */
    @SuppressWarnings("squid:S4275")
    public String getText() {
        return replacement;
    }

    /**
     * Returns the token template.
     *
     * @return the {@link TokenTemplate}.
     */
    public TokenTemplate getTemplate() {
        return template;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(template).append(BEGIN_TOKEN, beginToken).append(END_TOKEN, endToken)
                .toString();
    }
}
