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
package com.anrisoftware.globalpom.reflection.beans

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see BeanField
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
class BeanFieldTest {

    @Test
    void "field name for field"() {
        def object = foo.class.getDeclaredField("fieldFoo")
        assert beanField.toFieldName(object) == "fieldFoo"
    }

    @Test
    void "field name for method"() {
        def object = foo.class.getDeclaredMethod("getFieldBar")
        assert beanField.toFieldName(object) == "fieldBar"
    }

    static class Foo {

        public Object fieldFoo

        public Object getFieldBar() {
        }
    }

    Foo foo

    BeanField beanField

    @BeforeEach
    void createBeanField() {
        beanField = injector.getInstance(BeanField)
        foo = new Foo()
    }

    static Injector injector = Guice.createInjector new BeansModule()
}
