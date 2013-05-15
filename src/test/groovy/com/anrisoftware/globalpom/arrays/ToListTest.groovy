package com.anrisoftware.globalpom.arrays

import org.junit.Test

/**
 * @see ToList
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ToListTest {

	@Test
	void "to list"() {
		inputs.each {
			assert ToList.toList(it.obj) == it.list
		}
	}

	static inputs = [
		[obj: ["A", "B"], list: ["A", "B"]],
		[obj: [1.0d, 2.0d] as double[], list: [1.0d, 2.0d]],
		[obj: [1l, 2l] as long[], list: [1l, 2l]],
		[obj: ["A", "B"] as String[], list: ["A", "B"]],
		[obj: "A", list: ["A"]],
	]
}
