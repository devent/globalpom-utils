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

import static java.util.regex.Pattern.compile;

import java.util.regex.Pattern;

/**
 * Converts a string to a underscore format. Example:
 * 
 * <pre>
 * CamelCase -> camel_case
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
	 * @param string
	 *            the string to convert.
	 * 
	 * @return the converted string.
	 */
	public String convert(String string) {
		return convert(string, true);
	}

	/**
	 * Converts the specified string to underscore format.
	 * 
	 * @param string
	 *            the string to convert.
	 * 
	 * @param lowerCase
	 *            set to {@code true} to convert to lower case, {@code false} to
	 *            leave the save case.
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
