/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
        DefaultTokensTemplate worker = factory.create tokens, template,
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
        DefaultTokensTemplate worker = factory.create tokens, template,
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
        DefaultTokensTemplate worker = factory.create tokens, template,
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
        DefaultTokensTemplate worker = factory.create tokens, template,
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
        DefaultTokensTemplate worker = factory.create tokens, template,
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
        DefaultTokensTemplate worker = factory.create tokens, template,
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
        DefaultTokensTemplate worker = factory.create tokens, template,
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
        DefaultTokensTemplate worker = factory.create tokens, template,
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
        DefaultTokensTemplate worker = factory.create tokens, template,
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

    static DefaultTokensTemplateFactory factory

    @BeforeClass
    static void createFactories() {
        toStringStyle
        injector = createInjector()
        factory = injector.getInstance DefaultTokensTemplateFactory
    }

    static Injector createInjector() {
        Guice.createInjector(new TokensTemplateWorkerModule())
    }
}
