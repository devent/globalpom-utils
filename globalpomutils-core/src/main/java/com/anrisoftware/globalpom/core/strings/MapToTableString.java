/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import com.anrisoftware.propertiesutils.ContextProperties;
import com.anrisoftware.propertiesutils.ContextPropertiesFactory;

/**
 * Converts the map into a good looking table for easy of debugging.
 * 
 * <pre>
 * Aaa := Bbb
 * Ccc := Ddd
 * </pre>
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.2
 */
public class MapToTableString implements Appendable {

    /**
     * The context properties context.
     * 
     * @since 1.3
     */
    public static final String PROPERTIES_CONTEXT = MapToTableString.class
            .getPackage().getName();

    /**
     * The name of the null string value property.
     * 
     * @since 1.3
     */
    public static final String NULL_STRING_PROPERTY = "null_string";

    /**
     * The name of the line separator value property.
     * 
     * @since 1.3
     */
    public static final String LINE_SEPARATOR_PROPERTY = "line_separator";

    /**
     * The name of the values delimiter value property.
     * 
     * @since 1.3
     */
    public static final String VALUES_DELIMITER_PROPERTY = "values_delimiter";

    private static final String NULL_STRING_DEFAULT = "NULL";

    private static final String VALUES_DELIMITER_DEFAULT = ":=";

    private static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

    private static final URL PROPERTIES_RESOURCE = MapToTableString.class
            .getResource("/table_printer.properties");

    /**
     * Creates the converter with default properties.
     * 
     * @param appendable
     *            the {@link Appendable} to append the converted string.
     * 
     * @return the {@link MapToTableString}.
     */
    public static MapToTableString withDefaults(Appendable appendable) {
        try {
            return new MapToTableString(
                    new ContextPropertiesFactory(MapToTableString.class)
                            .fromResource(PROPERTIES_RESOURCE).withReplacements(
                                    System.getProperties()),
                    appendable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Converts the specified map to a string representation with default
     * properties.
     * 
     * @param map
     *            the {@link Map}.
     * 
     * @return the string representation of the map.
     */
    public static String mapToString(Map<?, ?> map) {
        return withDefaults(new StringBuilder()).append(map).toString();
    }

    private final String valuesDelimeter;

    private final String lineSeparator;

    private final String nullString;

    private final Appendable appendable;

    /**
     * Sets the properties for the converter and where to append the string
     * representation.
     * 
     * @param p
     *            the {@link Properties}:
     *            <dl>
     *            <dt>
     *            {@code com.anrisoftware.globalpom.core.external.strings.values_delimiter}
     *            </dt>
     *            <dd>The string to use to separate the value from the key.</dd>
     * 
     *            <dt>{@code com.anrisoftware.globalpom.core.external.strings.line_separator}
     *            </dt>
     *            <dd>The string to use to separate the entries.</dd>
     * 
     *            <dt>{@code com.anrisoftware.globalpom.core.external.strings.null_string}
     *            </dt>
     *            <dd>The string to use for {@code null} keys or values.</dd>
     *            </dl>
     * 
     * @param appendable
     *            the {@link Appendable} to append the string representation.
     * 
     * @since 1.3
     */
    public MapToTableString(Properties p, Appendable appendable) {
        this(new ContextProperties(MapToTableString.class, p), appendable);
    }

    /**
     * Sets the properties for the converter and where to append the string
     * representation.
     * 
     * @param p
     *            the {@link ContextProperties}:
     *            <dl>
     *            <dt>
     *            {@code com.anrisoftware.globalpom.core.external.strings.values_delimiter}
     *            </dt>
     *            <dd>The string to use to separate the value from the key.</dd>
     * 
     *            <dt>{@code com.anrisoftware.globalpom.core.external.strings.line_separator}
     *            </dt>
     *            <dd>The string to use to separate the entries.</dd>
     * 
     *            <dt>{@code com.anrisoftware.globalpom.core.external.strings.null_string}
     *            </dt>
     *            <dd>The string to use for {@code null} keys or values.</dd>
     *            </dl>
     * 
     * @param appendable
     *            the {@link Appendable} to append the string representation.
     */
    public MapToTableString(ContextProperties p, Appendable appendable) {
        this.appendable = appendable;
        this.valuesDelimeter = p.getProperty(VALUES_DELIMITER_PROPERTY,
                VALUES_DELIMITER_DEFAULT);
        this.lineSeparator = p.getProperty(LINE_SEPARATOR_PROPERTY,
                LINE_SEPARATOR);
        this.nullString = p.getProperty(NULL_STRING_PROPERTY,
                NULL_STRING_DEFAULT);
    }

    /**
     * Converts the specified map to a string representation and append it.
     * 
     * @param map
     *            the {@link Map}.
     * 
     * @return this {@link MapToTableString}.
     */
    public MapToTableString append(Map<?, ?> map) {
        int index = 0, size = map.size();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            append(toNullString(entry.getKey()));
            append(" ");
            append(valuesDelimeter);
            append(" ");
            append(toNullString(entry.getValue()));
            appendIfNotLast(lineSeparator, (++index) == size);
        }
        return this;
    }

    private void appendIfNotLast(String s, boolean last) {
        if (!last) {
            append(s);
        }
    }

    private String toNullString(Object value) {
        return value != null ? value.toString() : nullString;
    }

    @Override
    public Appendable append(CharSequence csq) {
        try {
            return appendable.append(csq);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) {
        try {
            return appendable.append(csq, start, end);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Appendable append(char c) {
        try {
            return appendable.append(c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return appendable.toString();
    }
}
