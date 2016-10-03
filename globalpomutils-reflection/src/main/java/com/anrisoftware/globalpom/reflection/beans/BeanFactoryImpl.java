/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
