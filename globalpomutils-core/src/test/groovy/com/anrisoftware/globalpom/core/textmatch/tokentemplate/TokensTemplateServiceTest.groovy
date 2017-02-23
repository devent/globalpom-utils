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
package com.anrisoftware.globalpom.core.textmatch.tokentemplate

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.apache.sling.testing.mock.osgi.junit.OsgiContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import com.anrisoftware.globalpom.core.textmatch.tokentemplate.TokenMarker
import com.anrisoftware.globalpom.core.textmatch.tokentemplate.TokenTemplate
import com.anrisoftware.globalpom.core.textmatch.tokentemplate.TokensTemplate
import com.anrisoftware.globalpom.core.textmatch.tokentemplate.TokensTemplateService
import com.anrisoftware.globalpom.core.textmatch.tokentemplate.TokensTemplateServiceImpl
import com.anrisoftware.globalpom.utils.TestUtils

/**
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Slf4j
class TokensTemplateServiceTest {

    @Rule
    public final OsgiContext context = new OsgiContext()

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

    @Before
    void createFactories() {
        TestUtils.toStringStyle
        this.service = context.registerInjectActivateService(new TokensTemplateServiceImpl(), null)
    }
}
