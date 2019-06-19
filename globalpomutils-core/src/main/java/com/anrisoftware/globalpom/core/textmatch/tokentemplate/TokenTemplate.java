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
package com.anrisoftware.globalpom.core.textmatch.tokentemplate;

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
import static java.util.regex.Pattern.compile;

import java.io.Serializable;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Holds the search text and replacement.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@SuppressWarnings("serial")
public class TokenTemplate implements Serializable {

    private static final String ESCAPE = "escape";

    private static final String ENCLOSE = "enclose";

    private static final String APPEND = "append";

    private static final String FLAGS = "flags";

    private final String search;

    private final String replace;

    private int flags;

    private boolean append;

    private boolean enclose;

    private boolean escape;

    /**
     * @see #TokenTemplate(String, String, int, boolean)
     */
    public TokenTemplate(Map<String, Object> args, String search, String replace) {
        this(search, replace);
        if (args.containsKey(FLAGS)) {
            this.flags = (Integer) args.get(FLAGS);
        }
        if (args.containsKey(APPEND)) {
            this.append = (Boolean) args.get(APPEND);
        }
        if (args.containsKey(ENCLOSE)) {
            this.enclose = (Boolean) args.get(ENCLOSE);
        }
        if (args.containsKey(ESCAPE)) {
            this.escape = (Boolean) args.get(ESCAPE);
        }
    }

    /**
     * @see #TokenTemplate(String, String, int, boolean)
     */
    public TokenTemplate(String search, String replace) {
        this(search, replace, 0);
    }

    /**
     * Sets the search and replacement string.
     * 
     * @param search
     *            the search string.
     * 
     * @param replace
     *            the replacement string.
     * 
     * @param flags
     *            Match flags, a bit mask that may include
     *            <ul>
     *            <li>{@link Pattern#CASE_INSENSITIVE},
     *            <li>{@link Pattern#MULTILINE},
     *            <li>{@link Pattern#DOTALL},
     *            <li>{@link Pattern#UNICODE_CASE},
     *            <li>{@link Pattern#CANON_EQ},
     *            <li>{@link Pattern#UNIX_LINES},
     *            <li>{@link Pattern#LITERAL},
     *            <li>{@link Pattern#UNICODE_CHARACTER_CLASS} and
     *            <li>{@link Pattern#COMMENTS}.
     *            </ul>
     */
    public TokenTemplate(String search, String replace, int flags) {
        this.search = search;
        this.replace = replace;
        this.flags = flags;
        this.append = true;
        this.enclose = true;
        this.escape = true;
    }

    /**
     * Returns the search string.
     * 
     * @return the search string
     */
    public String getSearch() {
        return search;
    }

    /**
     * Returns the replacement string.
     * 
     * @return the replacement string
     */
    public String getReplace() {
        return replace;
    }

    /**
     * Returns the pattern with the begin and end token.
     * 
     * @param beginToken
     *            the begin token.
     * 
     * @param endToken
     *            the end token.
     * 
     * @return the {@link Pattern}.
     */
    public Pattern toPattern(String beginToken, String endToken) {
        String config = format("(%s)", search);
        String pattern;
        pattern = format("(%s\\n)?%s(\\n%s)?", beginToken, config, endToken);
        return compile(pattern, flags);
    }

    /**
     * Returns the replace string with the begin and end token.
     * 
     * @param beginToken
     *            the begin token.
     * 
     * @param endToken
     *            the end token.
     * 
     * @return the replace string.
     */
    public String toReplace(String beginToken, String endToken) {
        if (enclose) {
            return format("%s\n%s\n%s", beginToken, replace, endToken);
        } else {
            return replace;
        }
    }

    /**
     * Sets if the replacement should be appended if the search string was not
     * found.
     * 
     * @param append
     *            set to {@code true} to append the replacement.
     */
    public void setAppend(boolean append) {
        this.append = append;
    }

    /**
     * Returns if the replacement should be appended if the search string was
     * not found.
     * 
     * @return {@code true} to append the replacement.
     */
    public boolean isAppend() {
        return append;
    }

    /**
     * Sets if the replacement should be enclosed in the begin and end token.
     * 
     * @param append
     *            set to {@code true} to enclose.
     */
    public void setEnclose(boolean enclose) {
        this.enclose = enclose;
    }

    /**
     * Returns if the replacement should be enclosed in the begin and end token.
     * 
     * @return {@code true} to enclose.
     */
    public boolean isEnclose() {
        return enclose;
    }

    /**
     * Sets if the replacement should escape all dollar characters {@code $}.
     * 
     * @param escape
     *            set to {@code true} to escape.
     */
    public void setEscape(boolean escape) {
        this.escape = escape;
    }

    /**
     * Returns if the replacement should escape all dollar characters {@code $}.
     * 
     * @return {@code true} to escape.
     */
    public boolean isEscape() {
        return escape;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("search", search)
                .append("replace", replace).toString();
    }

}
