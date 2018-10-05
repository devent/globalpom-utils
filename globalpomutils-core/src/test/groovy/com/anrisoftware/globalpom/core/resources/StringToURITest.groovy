package com.anrisoftware.globalpom.core.resources

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import groovy.util.logging.Slf4j

/**
 * @see StringToURI
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class StringToURITest {

    static Collection<Object[]> data() {
        [
            [
                "file.txt",
                new URI("file:file.txt")
            ],
            [
                "file://file.txt",
                new URI("file://file.txt")
            ],
            [
                "file:/file.txt",
                new URI("file:///file.txt")
            ],
        ] as Object[][]
    }

    @ParameterizedTest(name = "{index} => path={0}, expected={1}")
    @MethodSource("data")
    void "convert to URI"(String path, URI expected) {
        assert StringToURI.toURI(path) == expected
    }
}
