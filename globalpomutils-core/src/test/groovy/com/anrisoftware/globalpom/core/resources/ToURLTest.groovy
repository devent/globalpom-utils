package com.anrisoftware.globalpom.core.resources

import org.junit.jupiter.api.Test

import groovy.util.logging.Slf4j

/**
 * @see ToURL
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Slf4j
class ToURLTest {

    @Test
    void "convert to URL"() {
        inputs.each {
            def url = ToURL.toURL(it.path)
            log.info "Converted path '{}' to '{}'", it.path, url
            assert url == it.url
        }
    }

    static inputs = [
        [path: "file.txt", url: new URL("file:file.txt")],
        [path: "file://file.txt", url: new URL("file://file.txt")],
        [path: new File("file.txt"), url: new File("file.txt").toURI().toURL()],
        [path: new URL("file://file.txt"), url: new URL("file://file.txt")],
        [path: new URI("file://file.txt"), url: new URL("file://file.txt")],
    ]
}
