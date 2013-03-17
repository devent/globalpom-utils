package com.anrisoftware.globalpom.strings

import org.junit.Test

class MapToTableStringTest {

	@Test
	void "with defaults to string"() {
		def string = MapToTableString.mapToString map
		assert string == """Aaa := Bbb
Ccc := Ddd"""
	}

	static final map = ["Aaa": "Bbb", "Ccc": "Ddd"]
}
