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
package com.anrisoftware.globalpom.core.strings;

import static java.util.regex.Pattern.compile;

import java.util.regex.Pattern;

/**
 * Converts a string to a underscore format. Example:
 *
 * <pre>
 * CamelCase -&gt; camel_case
 * </pre>
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
public class ToUnderscore {

    private static final Pattern pattern = compile("(.)([A-Z]+)");

    private static final String replace = "$1_$2";

    /**
     * Converts the specified string to underscore format in lower case.
     *
     * @param string the string to convert.
     *
     * @return the converted string.
     */
    public String convert(String string) {
        return convert(string, true);
    }

    /**
     * Converts the specified string to underscore format.
     *
     * @param string    the string to convert.
     *
     * @param lowerCase set to {@code true} to convert to lower case, {@code false}
     *                  to leave the save case.
     *
     * @return the converted string.
     */
    public String convert(String string, boolean lowerCase) {
        string = pattern.matcher(string).replaceAll(replace);
        if (lowerCase) {
            string = string.toLowerCase();
        }
        return string;
    }
}
