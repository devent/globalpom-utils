/**
 * Copyright © 2014 Erwin Müller (erwin.mueller@anrisoftware.com)
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

package com.anrisoftware.globalpom.reflection.annotationclass;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.beans.PropertyVetoException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.Builder;

import com.anrisoftware.globalpom.reflection.annotations.AnnotationAccess;
import com.anrisoftware.globalpom.reflection.annotations.AnnotationAccessFactory;
import com.anrisoftware.globalpom.reflection.beans.BeanAccess;
import com.anrisoftware.globalpom.reflection.beans.BeanAccessFactory;
import com.anrisoftware.globalpom.reflection.beans.BeanFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * <p>
 * Creates an instance from an annotation attribute.
 * </p>
 * <p>
 * If the parent object have a field that the attribute name references then the
 * field's value is returned. If the field have no instance, the instance is
 * created with the default constructor and the field is set. If the attribute
 * name is set to a class type, the class type is instantiated and returned.
 * </p>
 * <p>
 * The attribute is suffixed with "Class" so that it reference the class type
 * that should be instantiated.
 * </p>
 * <h2>Examples</h2>
 *
 * <h3>With class type field</h3>
 *
 * <pre>
 * class Bean {
 *
 *     &#064;SomeAnnotation(model = &quot;someModel&quot;)
 *     public Object someField;
 *
 *     public SomeModel someModel;
 * }
 *
 * public SomeModel createModel(Bean bean, Field someField) {
 *     return create(bean, SomeAnnotation.class, someField).forAttribute(&quot;model&quot;).build();
 * }
 * </pre>
 *
 * <h3>With class type</h3>
 *
 * <pre>
 * class Bean {
 *
 *     &#064;SomeAnnotation(modelClass = SomeModel.class)
 *     public Object someField;
 * }
 *
 * public SomeModel createModel(Bean bean, Field someField) {
 *     return create(bean, SomeAnnotation.class, someField).forAttribute(&quot;model&quot;).build();
 * }
 * </pre>
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class AnnotationClass<ClassType> implements Builder<ClassType> {

    /**
     * @see AnnotationClassFactory#create(Object, Class, AccessibleObject)
     *
     * @param parent      the parent {@link Object}
     *
     * @param annotation  the annotation {@link Class}
     *
     * @param object      the {@link AccessibleObject}
     *
     * @param <ClassType> the class type.
     *
     * @return the {@link AnnotationClass}
     */
    @SuppressWarnings("unchecked")
    public static <ClassType> AnnotationClass<ClassType> create(Object parent, Class<? extends Annotation> annotation,
            AccessibleObject object) {
        return (AnnotationClass<ClassType>) AnnotationClassModule.getInjector()
                .getInstance(AnnotationClassFactory.class).create(parent, annotation, object);
    }

    private final AnnotationClassLogger log;

    private final BeanAccessFactory beanAccessFactory;

    private final AnnotationAccess fieldAnnotation;

    private final BeanFactory beanFactory;

    private String attributeName;

    private final Object parent;

    private ClassType defaultValue;

    /**
     * @see AnnotationClassFactory#create(Class, AccessibleObject)
     *
     * @param logger            the {@link AnnotationClassLogger}
     *
     * @param accessFactory     the {@link AnnotationAccessFactory}
     *
     * @param beanAccessFactory the {@link BeanAccessFactory}
     *
     * @param beanFactory       the {@link BeanFactory}
     *
     * @param parent            the parent {@link Object}
     *
     * @param annotation        the annotation {@link Class}
     *
     * @param object            the {@link AccessibleObject}
     *
     */
    @Inject
    AnnotationClass(AnnotationClassLogger logger, AnnotationAccessFactory accessFactory,
            BeanAccessFactory beanAccessFactory, BeanFactory beanFactory, @Assisted Object parent,
            @Assisted Class<? extends Annotation> annotation, @Assisted AccessibleObject object) {
        this.log = logger;
        this.parent = parent;
        this.fieldAnnotation = accessFactory.create(annotation, object);
        this.beanAccessFactory = beanAccessFactory;
        this.beanFactory = beanFactory;
    }

    /**
     * Sets the default value that is returned if neither the object field nor the
     * object class is set in the annotation.
     *
     * @param defaultValue the default value {@link Object} or {@code null}.
     *
     * @return this {@link AnnotationClass}.
     */
    public AnnotationClass<ClassType> withDefault(ClassType defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    /**
     * Sets the attribute name of the annotation that is either the name of the
     * field or the class type.
     *
     * @param name the attribute name.
     *
     * @return this {@link AnnotationClass}.
     *
     * @throws NullPointerException     if the specified name is {@code null}.
     *
     * @throws IllegalArgumentException if the specified name is empty.
     */
    public AnnotationClass<ClassType> forAttribute(String name) {
        this.attributeName = name;
        return this;
    }

    /**
     * Returns the class type object.
     *
     * @return the field if the field name was specified; instantiates and returns
     *         the class object if the class type was specified; {@code null} if
     *         neither was specified in the annotation.
     *
     * @throws NullPointerException     if the attribute name is {@code null}.
     *
     * @throws IllegalArgumentException if the specified attribute name is empty.
     */
    @Override
    public ClassType build() {
        log.checkAttributeName(attributeName);
        return createInstance();
    }

    private ClassType createInstance() {
        String fieldName = fieldAnnotation.getValue(attributeName);
        if (!isEmpty(fieldName)) {
            return createFromField(fieldName);
        } else {
            return createModelClass();
        }
    }

    private ClassType createFromField(String fieldName) {
        BeanAccess access = beanAccessFactory.create(fieldName, parent);
        ClassType value = access.getValue();
        return value == null ? createModelFromField(access) : value;
    }

    @SuppressWarnings("unchecked")
    private ClassType createModelFromField(BeanAccess access) {
        Class<? extends ClassType> type;
        type = (Class<? extends ClassType>) access.getType();
        ClassType model = beanFactory.create(type);
        try {
            access.setValue(model);
            return model;
        } catch (PropertyVetoException e) {
            throw new NullPointerException();
        }
    }

    private ClassType createModelClass() {
        ClassType model = null;
        String name = getAttributeClass();
        if (fieldAnnotation.haveValue(name)) {
            model = createModelClass0(name);
        }
        return model;
    }

    private ClassType createModelClass0(String name) {
        Class<? extends ClassType>[] type = fieldAnnotation.getValue(name);
        if (type.length < 1) {
            return defaultValue;
        }
        log.checkClassAttribute(type, attributeName);
        return beanFactory.create(type[0]);
    }

    private String getAttributeClass() {
        return attributeName + "Class";
    }

}
