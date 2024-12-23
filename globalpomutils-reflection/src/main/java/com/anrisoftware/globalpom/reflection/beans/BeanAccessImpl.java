/*
 * Copyright 2014-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.reflect.MethodUtils.getAccessibleMethod;

import java.beans.PropertyVetoException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Access the fields of an bean object.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanAccessImpl implements BeanAccess {

    private static final String SETTER_PREFIX = "set";

    private static final String[] GETTER_PREFIXES = { "get", "is" };

    private final Field field;

    private final String fieldName;

    private final Object bean;

    private final Method setter;

    private final Method getter;

    private final Class<?> fieldType;

    /**
     * @see BeanAccessFactory#create(String, Object)
     */
    @AssistedInject
    BeanAccessImpl(@Assisted String fieldName, @Assisted Object bean) {
        notNull(fieldName, "field-name = null");
        notNull(bean, "bean = null");
        this.fieldName = fieldName;
        this.field = findField(fieldName, bean);
        this.bean = bean;
        this.getter = findGetter(fieldName, bean);
        this.fieldType = findFieldType(fieldName, bean, field, getter);
        this.setter = findSetter(fieldName, fieldType, bean);
    }

    private Class<?> findFieldType(String fieldName, Object bean, Field field,
            Method getter) {
        if (getter != null) {
            return getter.getReturnType();
        }
        if (field != null) {
            return field.getType();
        }
        throw new NoFieldGetterDefinedException(bean, fieldName);
    }

    /**
     * @see BeanAccessFactory#create(Field, Object)
     */
    @AssistedInject
    BeanAccessImpl(@Assisted Field field, @Assisted Object bean) {
        notNull(field, "field = null");
        notNull(bean, "bean = null");
        this.fieldName = field.getName();
        this.field = findField(fieldName, bean);
        this.fieldType = field.getType();
        this.bean = bean;
        this.setter = findSetter(fieldName, fieldType, bean);
        this.getter = findGetter(fieldName, bean);
    }

    private Field findField(String fieldName, Object bean) {
        return FieldUtils.getField(bean.getClass(), fieldName, true);
    }

    private Method findSetter(String fieldName, Class<?> type, Object bean) {
        String name = getSetterName(fieldName);
        return getAccessibleMethod(bean.getClass(), name, type);
    }

    private String getSetterName(String name) {
        StringBuilder builder = new StringBuilder();
        char nameChar = Character.toUpperCase(name.charAt(0));
        builder.append(SETTER_PREFIX);
        builder.append(nameChar);
        builder.append(name.substring(1));
        return builder.toString();
    }

    private Method findGetter(String fieldName, Object bean) {
        for (String prefix : GETTER_PREFIXES) {
            String name = getGetterName(fieldName, prefix);
            Method method = getAccessibleMethod(bean.getClass(), name);
            if (method != null) {
                return method;
            }
        }
        return null;
    }

    private String getGetterName(String name, String prefix) {
        StringBuilder builder = new StringBuilder();
        char nameChar = Character.toUpperCase(name.charAt(0));
        builder.append(prefix);
        builder.append(nameChar);
        builder.append(name.substring(1));
        return builder.toString();
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public Method getGetter() {
        return getter;
    }

    @Override
    public Method getSetter() {
        return setter;
    }

    @Override
    public AccessibleObject getGettterObject() {
        return getter != null ? getter : field;
    }

    @Override
    public Class<?> getType() {
        return fieldType;
    }

    @Override
    public <T> T getValue() {
        if (getter != null) {
            return getValueFromGetter(getter, bean);
        } else {
            return getValueFromField(field, bean);
        }
    }

    private <T> T getValueFromGetter(Method getter, Object bean) {
        try {
            return toType(getter.invoke(bean));
        } catch (IllegalAccessException e) {
            throw new GetValueError(e, bean, fieldName, getter);
        } catch (IllegalArgumentException e) {
            throw new GetValueError(e, bean, fieldName, getter);
        } catch (InvocationTargetException e) {
            throw new GetValueError(e.getTargetException(), bean, fieldName,
                    getter);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T toType(Object object) {
        return (T) object;
    }

    private <T> T getValueFromField(Field field, Object bean) {
        try {
            return toType(FieldUtils.readField(field, bean, true));
        } catch (IllegalAccessException e) {
            throw new GetValueError(e, bean, fieldName);
        }
    }

    @Override
    public void setValue(Object value) throws PropertyVetoException {
        boolean set = setValueWithSetter(setter, value);
        if (!set) {
            setValueToField(value, field, bean);
        }
    }

    private boolean setValueWithSetter(Method setter, Object value)
            throws PropertyVetoException {
        if (setter == null) {
            return false;
        }
        try {
            setter.invoke(bean, value);
            return true;
        } catch (IllegalAccessException e) {
            throw new SetValueError(e, bean, fieldName, setter);
        } catch (IllegalArgumentException e) {
            throw new SetValueError(e, bean, fieldName, setter);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof PropertyVetoException) {
                PropertyVetoException ex = (PropertyVetoException) e.getCause();
                throw new ValueVetoedError(ex, bean, fieldName, setter);
            }
            throw new SetValueError(e, bean, fieldName, setter);
        }
    }

    private void setValueToField(Object value, Field field, Object bean) {
        try {
            FieldUtils.writeField(field, bean, value, true);
        } catch (IllegalAccessException e) {
            throw new SetValueError(e, bean, fieldName);
        }
    }

}
