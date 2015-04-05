/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-reflection.
 *
 * globalpomutils-reflection is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-reflection is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-reflection. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.reflection.beans;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Singleton;

/**
 * Field utility.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
@Singleton
public class BeanField {

	private static final String[] GETTER_PREFIXES = { "get", "is" };

	private final Pattern getterPattern;

	public BeanField() {
		this.getterPattern = compile(getGetterPattern());
	}

	private String getGetterPattern() {
		StringBuilder str = new StringBuilder();
		str.append("(?:");
		int last = GETTER_PREFIXES.length - 1;
		for (int i = 0; i < last; i++) {
			str.append(format("(?:%s)|", GETTER_PREFIXES[i]));
		}
		str.append(format("(?:%s)", GETTER_PREFIXES[last]));
		str.append(")(.+)");
		return str.toString();
	}

	/**
	 * Returns the field name for the field or method. The method needs to be a
	 * getter method, with no parameter and with prefix {@code is} or
	 * {@code get}.
	 * 
	 * @param object
	 *            the {@link AccessibleObject}.
	 * 
	 * @return the field name.
	 */
	public String toFieldName(AccessibleObject object) {
		if (object instanceof Field) {
			return ((Field) object).getName();
		}
		if (object instanceof Method) {
			String name = ((Method) object).getName();
			Matcher matcher = getterPattern.matcher(name);
			matcher.find();
			return uncapitalize(matcher.group(1));
		}
		return null;
	}
}
