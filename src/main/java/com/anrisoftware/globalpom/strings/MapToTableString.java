/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.strings;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

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
 * @since 1.0
 */
public class MapToTableString implements Appendable {

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
			return new MapToTableString(new ContextPropertiesFactory(
					MapToTableString.class).fromResource(PROPERTIES_RESOURCE)
					.withReplacements(System.getProperties()), appendable);
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
	 *            the {@link ContextProperties}:
	 *            <dl>
	 *            <dt>{@code com.anrisoftware.globalpom.strings.values_delimeter}
	 *            </dt>
	 *            <dd>The string to use to separate the value froom the key.</dd>
	 * 
	 *            <dt>{@code com.anrisoftware.globalpom.strings.line_separator}</dt>
	 *            <dd>The string to use to separate the entries.</dd>
	 * 
	 *            <dt>{@code com.anrisoftware.globalpom.strings.null_string}</dt>
	 *            <dd>The string to use for {@code null} keys or values.</dd>
	 *            </dl>
	 * 
	 * @param appendable
	 *            the {@link Appendable} to append the string representation.
	 */
	public MapToTableString(ContextProperties p, Appendable appendable) {
		this.appendable = appendable;
		this.valuesDelimeter = p.getProperty("values_delimeter");
		this.lineSeparator = p.getProperty("line_separator");
		this.nullString = p.getProperty("null_string");
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
