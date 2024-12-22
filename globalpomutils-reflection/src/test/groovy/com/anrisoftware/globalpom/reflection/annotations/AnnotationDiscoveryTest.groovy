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
package com.anrisoftware.globalpom.reflection.annotations

import java.lang.reflect.Field

import org.apache.commons.lang3.reflect.FieldUtils
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
class AnnotationDiscoveryTest extends AnnotationUtils {

    @Test
    void "find annotations"() {
        int foundCounter = 0
        def filter = filterFactory.create([BeanAnnotation])
        def a = factory.create bean.bean, filter
        a.addListener([memberFound: { foundCounter++ }]as AnnotationListener)
        def result = a.call()
        assert result.size() == 3
        assert foundCounter == 3
    }

    static Injector injector

    static AnnotationDiscoveryFactory factory

    static AnnotationSetFilterFactory filterFactory

    static ParentBean bean

    static Field field

    @BeforeAll
    static void setupFactory() {
        injector = createInjector()
        factory = createAnnotationDiscoveryFactory(injector)
        filterFactory = createAnnotationSetFilterFactory(injector)
        bean = new ParentBean()
        field = FieldUtils.getField Bean, "annotatedField", true
    }
}
