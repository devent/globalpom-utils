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
package com.anrisoftware.globalpom.format.enums;

import static org.apache.commons.lang3.Validate.notNull;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * Formatter for an enumeration.
 * 
 * <pre>
 * static enum TestEnum {
 * 	FOO, BAR;
 * 
 * 	public static final Format FORMAT = EnumFormat.create(TestEnum.class);
 * }
 * 
 * enum = TestEnum.FORMAT.parseObject("FOO");
 * str  = TestEnum.FORMAT.format(TestEnum.FOO);
 * </pre>
 * 
 * @see Enum
 */
@SuppressWarnings("serial")
public class EnumFormat<E extends Enum<E>, EnumType extends Enum<E>> extends
		Format {

	/**
	 * Factory method with an unique name for static import.
	 * 
	 * @see EnumFormat#create(Class)
	 */
	public static <E extends Enum<E>, EnumType extends Enum<E>> EnumFormat<E, Enum<E>> createEnumFormat(
			Class<E> enumType) {
		return create(enumType);
	}

	/**
	 * Factory method to use type inference for the generic parameter.
	 * 
	 * @see EnumFormat#EnumFormat(Class)
	 */
	public static <E extends Enum<E>, EnumType extends Enum<E>> EnumFormat<E, Enum<E>> create(
			Class<E> enumType) {
		return new EnumFormat<E, Enum<E>>(enumType);
	}

	private final Class<E> enumType;

	/**
	 * Sets the enumeration type.
	 * 
	 * @param enumType
	 *            the {@link Class} type.
	 * 
	 * @throws NullPointerException
	 *             if the specified type is {@code null}.
	 */
	public EnumFormat(Class<E> enumType) {
		notNull(enumType);
		this.enumType = enumType;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return parse(source, pos);
	}

	/**
	 * @see #parseObject(String, ParsePosition)
	 */
	public EnumType parse(String source, ParsePosition pos) {
		try {
			source = source.substring(pos.getIndex()).toUpperCase();
			@SuppressWarnings("unchecked")
			EnumType item = (EnumType) Enum.valueOf(enumType, source);
			pos.setErrorIndex(-1);
			pos.setIndex(source.length());
			return item;
		} catch (IllegalArgumentException e) {
			pos.setIndex(0);
			pos.setErrorIndex(0);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
		if (obj instanceof Enum) {
			format(buff, (EnumType) obj);
		}
		return buff;
	}

	private void format(StringBuffer buff, EnumType obj) {
		buff.append(obj.toString());
	}
};
