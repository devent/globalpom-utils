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
package com.anrisoftware.globalpom.reflection.annotations;

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

import static java.util.Arrays.asList;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.event.EventListenerSupport;

import com.anrisoftware.globalpom.reflection.beans.BeanAccessFactory;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Search an object's fields for {@link Annotation}s and if an annotation is
 * found it will call the {@link AnnotationListener} callback. The annotations
 * are defined by a {@link AnnotationFilter}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationDiscoveryImpl implements AnnotationDiscovery {

    private final EventListenerSupport<AnnotationListener> listeners;

    private final AnnotationFilter filter;

    private final Object bean;

    private final BeanAccessFactory accessFactory;

    /**
     * @see AnnotationDiscoveryFactory#create(Object, AnnotationFilter)
     */
    @Inject
    AnnotationDiscoveryImpl(BeanAccessFactory accessFactory,
            @Assisted Object bean, @Assisted AnnotationFilter filter) {
        this.bean = bean;
        this.filter = filter;
        this.accessFactory = accessFactory;
        this.listeners = new EventListenerSupport<AnnotationListener>(
                AnnotationListener.class);
    }

    @Override
    public AnnotationFilter getFilter() {
        return filter;
    }

    @Override
    public void addListener(AnnotationListener l) {
        listeners.addListener(l);
    }

    @Override
    public void removeListener(AnnotationListener l) {
        listeners.removeListener(l);
    }

    @Override
    public Collection<AnnotationBean> call() {
        Set<AnnotationBean> result = new LinkedHashSet<AnnotationBean>();
        result = findFields(result, bean);
        result = findMethods(result, bean);
        return result;
    }

    private Set<AnnotationBean> findFields(Set<AnnotationBean> result,
            Object bean) {
        Class<? extends Object> type = bean.getClass();
        return findAnnotations(result, bean, asList(type.getFields()));
    }

    private Set<AnnotationBean> findMethods(Set<AnnotationBean> result,
            Object bean) {
        Class<? extends Object> type = bean.getClass();
        return findAnnotations(result, bean, asList(type.getMethods()));
    }

    private Set<AnnotationBean> findAnnotations(Set<AnnotationBean> result,
            Object bean, List<? extends AccessibleObject> members) {
        for (AccessibleObject member : members) {
            Annotation[] annotations = member.getDeclaredAnnotations();
            findAnnotations(result, member, bean, annotations);
        }
        return result;
    }

    private void findAnnotations(Set<AnnotationBean> result,
            AccessibleObject member, Object bean, Annotation[] annotations) {
        AnnotationBean event;
        for (Annotation annotation : annotations) {
            if (filter.accept(annotation)) {
                event = createAnnotationFoundEvent(bean, annotation, member);
                result.add(event);
                listeners.fire().memberFound(event);
            }
        }
    }

    private AnnotationBean createAnnotationFoundEvent(Object bean,
            Annotation annotation, AccessibleObject member) {
        if (member instanceof Field) {
            return new AnnotationEvent(bean, annotation, member, getValue(bean,
                    (Field) member));
        } else if (member instanceof Method) {
            return new AnnotationEvent(bean, annotation, member, getValue(bean,
                    (Method) member));
        }
        return null;
    }

    private Object getValue(Object bean, Method method) {
        String fieldName = getFieldName(method);
        return accessFactory.create(fieldName, bean).getValue();
    }

    private String getFieldName(Method method) {
        StringBuilder str = new StringBuilder();
        String name = method.getName();
        int offset = getStartOffset(name);
        if (offset == -1) {
            throw new MethodNotGetterError(this, bean, method);
        }
        char nameChar = Character.toLowerCase(name.charAt(offset));
        str.append(nameChar);
        str.append(name.substring(offset + 1));
        return str.toString();
    }

    private int getStartOffset(String name) {
        if (name.indexOf("get") != -1) {
            return 3;
        } else if (name.indexOf("is") != -1) {
            return 2;
        }
        return -1;
    }

    private Object getValue(Object bean, Field field) {
        return accessFactory.create(field, bean).getValue();
    }

}
