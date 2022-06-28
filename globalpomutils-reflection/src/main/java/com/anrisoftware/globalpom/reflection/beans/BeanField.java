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
package com.anrisoftware.globalpom.reflection.beans;

/*-
 * #%L
 * Global POM Utilities :: Reflection
 * %%
 * Copyright (C) 2014 - 2018 Advanced Natural Research Institute
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
