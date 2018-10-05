package com.anrisoftware.globalpom.core.resources

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.jupiter.api.Test

import groovy.util.logging.Slf4j

/**
 * @see ToURI
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class ToURITest {

    @Test
    void "convert to URI"() {
        [
            [path: "file.txt", uri: new URI("file:file.txt")],
            [path: "id_rsa", uri: new URI("file:id_rsa")],
            [path: "file://file.txt", uri: new URI("file://file.txt")],
            [path: new File("file.txt"), uri: new File("file.txt").toURI()],
            [path: new URL("file://file.txt"), uri: new URI("file://file.txt")],
            [path: new URI("file://file.txt"), uri: new URI("file://file.txt")],
            [path: "github.com:devent/wordpress-app-test.git", uri: new URI("github.com:devent/wordpress-app-test.git")],
            [path: "github.com:/devent/wordpress-app-test.git", uri: new URI("github.com:/devent/wordpress-app-test.git")],
        ].eachWithIndex { it, int k ->
            log.info '\n######### {}. case: {}', k, it
            def uri = ToURI.toURI(it.path).convert()
            assert uri == it.uri
        }
    }

    @Test
    void "with scp"() {
        [
            [path: "file.txt", uri: new URI("file:file.txt")],
            [path: "file://file.txt", uri: new URI("file://file.txt")],
            [path: new File("file.txt"), uri: new File("file.txt").toURI()],
            [path: new URL("file://file.txt"), uri: new URI("file://file.txt")],
            [path: new URI("file://file.txt"), uri: new URI("file://file.txt")],
            [path: "git@github.com:devent/wordpress-app-test.git", uri: new URI("ssh://git@github.com/devent/wordpress-app-test.git")],
            [path: "github.com:devent/wordpress-app-test.git", uri: new URI("ssh://github.com/devent/wordpress-app-test.git")],
            [path: "github.com:/devent/wordpress-app-test.git", uri: new URI("github.com:/devent/wordpress-app-test.git")],
        ].eachWithIndex { it, int k ->
            log.info '\n######### {}. case: {}', k, it
            def uri = ToURI.toURI(it.path).withScp().convert()
            assert uri == it.uri
        }
    }

    @Test
    void "expected ConvertException"() {
        [
            [path: "git@github.com:/devent/wordpress-app-test.git"],
        ].eachWithIndex { test, int k ->
            log.info '\n######### {}. case: {}', k, test
            shouldFailWith ConvertException, {
                def uri = ToURI.toURI(test.path).convert()
            }
        }
    }
}
