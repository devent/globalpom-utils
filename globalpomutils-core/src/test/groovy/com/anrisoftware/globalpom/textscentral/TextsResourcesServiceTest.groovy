/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
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
