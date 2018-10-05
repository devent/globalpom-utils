package com.anrisoftware.globalpom.core.textscentral

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.core.mnemonic.AcceleratorServiceImpl
import com.anrisoftware.globalpom.core.mnemonic.MnemonicServiceImpl
import com.anrisoftware.globalpom.utils.TestUtils
import com.anrisoftware.resources.binary.internal.binaries.BinariesServiceImpl
import com.anrisoftware.resources.binary.internal.maps.BinariesBundlesMapServiceImpl
import com.anrisoftware.resources.binary.internal.maps.BinariesMapServiceImpl
import com.anrisoftware.resources.binary.internal.resources.BinaryResourceServiceImpl
import com.anrisoftware.resources.texts.internal.maps.TextsBundlesMapServiceImpl
import com.anrisoftware.resources.texts.internal.maps.TextsMapDefaultServiceImpl
import com.anrisoftware.resources.texts.internal.texts.TextsServiceImpl

import groovy.util.logging.Slf4j

/**
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
class TextsResourcesServiceTest {

    final OsgiContext context = new OsgiContext()

    @Test
    void "texts resources"() {
        TextsResources resources = service.create()
        assert resources != null
    }

    TextsResourcesService service

    @BeforeEach
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
