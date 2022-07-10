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
package com.anrisoftware.globalpom.core.enumsformat;

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

import static org.apache.commons.lang3.Validate.notNull;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.core.enumsformat.EnumFormat;

/**
 * Formatter for an enumeration. Just a wrapper
 * {@link Enum#valueOf(Class, String)} for the {@link Format} API.
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
 * @param <E>        the enum type subclass.
 *
 * @param <EnumType> the type of the {@link Enum}.
 *
 * @see Enum
 */
@SuppressWarnings("serial")
public class EnumFormat<E extends Enum<E>, EnumType extends Enum<E>> extends Format {

    /**
     * Factory method with an unique name for static import.
     *
     * @see EnumFormat#create(Class)
     *
     * @param <E>        the enum type subclass.
     *
     * @param <EnumType> the type of the {@link Enum}.
     *
     * @param enumType   the {@link Class} type.
     *
     * @return the {@link EnumFormat}.
     */
    public static <E extends Enum<E>, EnumType extends Enum<E>> EnumFormat<E, Enum<E>> createEnumFormat(
            Class<E> enumType) {
        return create(enumType);
    }

    /**
     * Factory method to use type inference for the generic parameter.
     *
     * @see EnumFormat#EnumFormat(Class)
     *
     * @param <E>        the enum type subclass.
     *
     * @param <EnumType> the type of the {@link Enum}.
     *
     * @param enumType   the {@link Class} type.
     *
     * @return the {@link EnumFormat}.
     */
    public static <E extends Enum<E>, EnumType extends Enum<E>> EnumFormat<E, Enum<E>> create(Class<E> enumType) {
        return new EnumFormat<E, Enum<E>>(enumType);
    }

    private final Class<E> enumType;

    /**
     * Sets the enumeration type.
     *
     * @param enumType the {@link Class} type.
     *
     * @throws NullPointerException if the specified type is {@code null}.
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
     *
     * @param source the {@link String} source.
     *
     * @param pos the {@link ParsePosition}
     *
     * @return the {@link Enum}
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
