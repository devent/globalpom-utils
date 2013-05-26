package com.anrisoftware.globalpom.resources

import org.junit.Test

/**
 * @see ToURL
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ToURLTest {

	@Test
	void "convert to URI"() {
		inputs.each {
			assert ToURL.toURL(it.path) == it.uri
		}
	}

	static inputs = [
		[path: "file.txt", uri: new URL("file://file.txt")],
		[path: "file://file.txt", uri: new URL("file://file.txt")],
		[path: new File("file.txt"), uri: new File("file.txt").toURI().toURL()],
		[path: new URL("file://file.txt"), uri: new URL("file://file.txt")],
		[path: new URI("file://file.txt"), uri: new URL("file://file.txt")],
	]
}
