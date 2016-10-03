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
package com.anrisoftware.globalpom.textmatch.tokentemplate

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.BeforeClass
import org.junit.Test

import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Test tokens template.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
class TokensTemplateTest {

    @Test
    void "replace template without tokens"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo", "$replace")
        TokensTemplate worker = factory.create tokens, template,
                "foo\n"
        worker.replace()
        assertStringContent worker.text, """#BEGIN
$replace
#END

"""
    }

    @Test
    void "replace template without tokens with multiple entries"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo", "$replace")
        TokensTemplate worker = factory.create tokens, template,
                "foo\nbar\n"
        worker.replace()
        assertStringContent worker.text, """#BEGIN
$replace
#END

bar
"""
    }

    @Test
    void "replace template with tokens"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo", "$replace")
        TokensTemplate worker = factory.create tokens, template,
                "#BEGIN\nfoo\n#END\n"
        worker.replace()
        assertStringContent worker.text, """#BEGIN
$replace
#END
"""
    }

    @Test
    void "replace template with tokens with multiple entries"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo", "$replace")
        TokensTemplate worker = factory.create tokens, template,
                "#BEGIN\nfoo\n#END\nbar\n"
        worker.replace()
        assertStringContent worker.text, """#BEGIN
$replace
#END
bar
"""
    }

    @Test
    void "replace template with tokens with two entries"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo\nbar", "baz\nbaz")
        TokensTemplate worker = factory.create tokens, template,
                "#BEGIN\nfoo\nbar\n#END\n"
        worker.replace()
        assertStringContent worker.text, """#BEGIN
baz
baz
#END
"""
    }

    @Test
    void "replace template empty with two entries"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo\nbar", "baz\nbaz")
        TokensTemplate worker = factory.create tokens, template,
                ""
        worker.replace()
        assertStringContent worker.text, """#BEGIN
baz
baz
#END
"""
    }

    @Test
    void "append template with tokens to empty configuration"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo", "$replace")
        TokensTemplate worker = factory.create tokens, template,
                "\n"
        worker.replace()
        assertStringContent worker.text, """
#BEGIN
$replace
#END
"""
    }

    @Test
    void "append template with tokens to configuration"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo", "$replace")
        TokensTemplate worker = factory.create tokens, template,
                "bar\n"
        worker.replace()
        assertStringContent worker.text, """bar
#BEGIN
$replace
#END
"""
    }

    @Test
    void "serialize and append template with tokens to empty configuration"() {
        def replace = "bar"
        def tokens = new TokenMarker("#BEGIN", "#END\n")
        def template = new TokenTemplate("foo", "$replace")
        TokensTemplate worker = factory.create tokens, template,
                "\n"
        def workerB = reserialize worker
        workerB.replace()
        assertStringContent workerB.text, """
#BEGIN
$replace
#END
"""
    }

    static Injector injector

    static TokensTemplateFactory factory

    @BeforeClass
    static void createFactories() {
        toStringStyle
        injector = Guice.createInjector(new TokensTemplateModule())
        factory = injector.getInstance TokensTemplateFactory
    }
}
