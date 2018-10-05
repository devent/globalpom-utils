package com.anrisoftware.globalpom.core.system

import org.junit.jupiter.api.Test

import groovy.util.logging.Slf4j

/**
 * @see SystemSelector
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
@Slf4j
class SystemSelectorTest {

    @Test
    void "get system"() {
        def name = System.getProperty("os.name")
        def system = SystemSelector.getSystem()
        log.info "System for {}: {}", name, system
    }
}
