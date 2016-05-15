/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * Creates bean objects.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public class BeanFactoryImpl implements BeanFactory {

    @Override
    public <T> T create(Class<T> type) {
        try {
            return ConstructorUtils.invokeConstructor(type);
        } catch (NoSuchMethodException e) {
            throw new CreateBeanError(e, type);
        } catch (IllegalAccessException e) {
            throw new CreateBeanError(e, type);
        } catch (InvocationTargetException e) {
            throw new CreateBeanError(e.getTargetException(), type);
        } catch (InstantiationException e) {
            throw new CreateBeanError(e, type);
        }
    }

    @Override
    public <T> T create(String typeName) {
        try {
            @SuppressWarnings("unchecked")
            Class<T> type = (Class<T>) Class.forName(typeName);
            return create(type);
        } catch (ClassNotFoundException e) {
            throw new BeanTypeNotFoundError(e, typeName);
        }
    }
}
