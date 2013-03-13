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
