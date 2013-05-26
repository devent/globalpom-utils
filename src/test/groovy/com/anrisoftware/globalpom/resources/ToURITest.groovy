package com.anrisoftware.globalpom.resources

import org.junit.Test

/**
 * @see StringToURI
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ToURITest {

	@Test
	void "convert to URI"() {
		inputs.each {
			assert StringToURI.toURI(it.path) == it.uri
		}
	}

	static inputs = [
		[path: "file.txt", uri: new URI("file://file.txt")],
		[path: "file://file.txt", uri: new URI("file://file.txt")],
	]
}
