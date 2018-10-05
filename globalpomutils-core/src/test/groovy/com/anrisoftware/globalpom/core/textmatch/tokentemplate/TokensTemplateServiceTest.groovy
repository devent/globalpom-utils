package com.anrisoftware.globalpom.core.textmatch.tokentemplate

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.apache.sling.testing.mock.osgi.junit5.OsgiContext
import org.apache.sling.testing.mock.osgi.junit5.OsgiContextExtension
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

import com.anrisoftware.globalpom.utils.TestUtils

import groovy.util.logging.Slf4j

/**
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
@ExtendWith(OsgiContextExtension.class)
class TokensTemplateServiceTest {

    final OsgiContext context = new OsgiContext()

    @Test
    void "replace template without tokens"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo", "$replace")
        TokensTemplate worker = service.create tokens, template,
                "foo\n"
        worker.replace()
        assertStringContent worker.text, """#BEGIN
$replace
#END

"""
    }

    TokensTemplateService service

    @BeforeEach
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new TokensTemplateServiceImpl(), null)
    }
}
