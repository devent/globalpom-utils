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
package com.anrisoftware.globalpom.textscentral

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import com.anrisoftware.globalpom.mnemonic.AcceleratorServiceImpl
import com.anrisoftware.globalpom.mnemonic.MnemonicServiceImpl
import com.anrisoftware.globalpom.utils.TestUtils
import com.anrisoftware.resources.binary.internal.binaries.BinariesServiceImpl
import com.anrisoftware.resources.binary.internal.maps.BinariesBundlesMapServiceImpl
import com.anrisoftware.resources.binary.internal.maps.BinariesMapServiceImpl
import com.anrisoftware.resources.binary.internal.resources.BinaryResourceServiceImpl
import com.anrisoftware.resources.texts.internal.maps.TextsBundlesMapServiceImpl
import com.anrisoftware.resources.texts.internal.maps.TextsMapDefaultServiceImpl
import com.anrisoftware.resources.texts.internal.texts.TextsServiceImpl

/**
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
class TextsResourcesServiceTest {

    @Rule
    public final OsgiContext context = new OsgiContext()

    @Test
    void "texts resources"() {
        TextsResources resources = service.create()
        assert resources != null
    }

    TextsResourcesService service

    @Before
    void createFactories() {
        TestUtils.toStringStyle
        context.registerInjectActivateService(new AcceleratorServiceImpl(), null)
        context.registerInjectActivateService(new MnemonicServiceImpl(), null)
        context.registerInjectActivateService(new BinariesBundlesMapServiceImpl(), null)
        context.registerInjectActivateService(new BinariesMapServiceImpl(), null)
        context.registerInjectActivateService(new BinariesServiceImpl(), null)
        context.registerInjectActivateService(new BinaryResourceServiceImpl(), null)
        context.registerInjectActivateService(new TextsBundlesMapServiceImpl(), null)
        context.registerInjectActivateService(new TextsMapDefaultServiceImpl(), null)
        context.registerInjectActivateService(new TextsServiceImpl(), null)
        this.service = context.registerInjectActivateService(new TextsResourcesServiceImpl(), null)
    }
}
