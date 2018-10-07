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
package com.anrisoftware.globalpom.reflection.annotations

import java.lang.reflect.Field
import java.lang.reflect.Method

import org.apache.commons.lang3.reflect.FieldUtils
import org.apache.commons.lang3.reflect.MethodUtils
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.reflection.utils.Bean
import com.anrisoftware.globalpom.reflection.utils.BeanAnnotation
import com.anrisoftware.globalpom.reflection.utils.ParentBean
import com.google.inject.Injector

/**
 * Tests for {@link AnnotationAccessImpl}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class AnnotationAccessTest extends AnnotationUtils {

    @Test
    void "read annotation value"() {
        def access = factory.create BeanAnnotation, field
        def value = access.getValue()
        assertStringContent value, "Annotation Value"
    }

    @Test
    void "read annotation title value"() {
        def name = "title"
        def access = factory.create BeanAnnotation, field
        def value = access.getValue(name)
        assertStringContent value, "Annotation Title"
    }

    @Test
    void "read undefined annotation element"() {
        def name = "not defined"
        def access = factory.create BeanAnnotation, field
        shouldFailWith(GetValueError) {
            def value = access.getValue(name)
        }
    }

    @Test
    void "read annotation value from method"() {
        def access = factory.create BeanAnnotation, method
        def value = access.getValue()
        assertStringContent value, "Annotation Method"
    }

    static Injector injector

    static AnnotationAccessFactory factory

    static ParentBean bean

    static Field field

    static Method method

    @BeforeAll
    static void setupFactory() {
        injector = createInjector()
        factory = createAnnotationAccessFactory(injector)
        bean = new ParentBean()
        field = FieldUtils.getField Bean, "annotatedField", true
        method = MethodUtils.getAccessibleMethod Bean, "getAnnotatedMethod"
    }
}
