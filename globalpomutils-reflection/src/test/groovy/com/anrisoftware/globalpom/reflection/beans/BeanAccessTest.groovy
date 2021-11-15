/*
 * Copyright 2014-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.reflection.beans

import org.apache.commons.lang3.reflect.FieldUtils
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import com.anrisoftware.globalpom.reflection.utils.Bean
import com.anrisoftware.globalpom.reflection.utils.ParentBean
import com.google.inject.Injector

/**
 * Tests for {@link BeanAccessImpl}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanAccessTest extends BeanUtils {

    @Test
    void "read field value"() {
        def field = FieldUtils.getField Bean, "stringField", true
        def value = factory.create(field, bean.bean).getValue()
        assertStringContent value, "Text"
    }

    @Test
    void "read field value via getter"() {
        def field = FieldUtils.getField Bean, "getterField", true
        def value = factory.create(field, bean.bean).getValue()
        assertStringContent value, "Getter Text"
        assert bean.bean.getterOfGetterFieldCalled
    }

    @Test
    void "read field value via getter that throws exception"() {
        def field = FieldUtils.getField Bean, "getterFieldThatThrowsException", true
        shouldFailWith(GetValueError) {
            def value = factory.create(field, bean.bean).getValue()
        }
    }

    @Test
    void "read method value"() {
        def field = "methodOnly"
        def value = factory.create(field, bean.bean).getValue()
        assertStringContent value, "MethodOnly"
        assert bean.bean.getterMethodOnlyCalled
    }

    @Test
    void "write field value"() {
        def field = FieldUtils.getField Bean, "stringField", true
        String value = "value"
        factory.create(field, bean.bean).setValue(value)
        assertStringContent bean.bean.stringField, value
    }

    @Test
    void "write field value via setter"() {
        def field = FieldUtils.getField Bean, "setterField", true
        String value = "value"
        factory.create(field, bean.bean).setValue(value)
        assertStringContent bean.bean.setterField, value
        assert bean.bean.setterOfSetterFieldCalled
    }

    @Test
    void "write field value via setter that throws exception"() {
        def field = FieldUtils.getField Bean, "setterFieldThatThrowsException", true
        String value = "value"
        shouldFailWith(SetValueError) {
            factory.create(field, bean.bean).setValue(value)
        }
    }

    @Test
    void "write method value"() {
        def field = "methodOnly"
        String value = "value"
        factory.create(field, bean.bean).setValue(value)
        assert bean.bean.setterMethodOnlyCalled
    }

    @Test
    void "write method value with veto"() {
        def field = "getterMethodOnlyVetoException"
        String value = "value"
        shouldFailWith(ValueVetoedError) {
            factory.create(field, bean.bean).setValue(value)
        }
        assert bean.bean.setterMethodOnlyVetoExceptionCalled
    }

    static Injector injector

    static BeanAccessFactory factory

    ParentBean bean

    @BeforeEach
    void setupBean() {
        bean = new ParentBean()
    }

    @BeforeAll
    static void setupFactory() {
        injector = createInjector()
        factory = createFactory(injector)
    }
}
