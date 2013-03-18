package com.anrisoftware.globalpom.exceptions

import org.junit.Test

class ContextTest {

	@Test
	void "context with exception"() {
		def message = "Exception message"
		def ex = new Exception(message)
		def context = new Context(ex)
		context.addContext("A1", "value 1")
		context.addContext("A2", "value 2")
		assert context.toString() ==
		"""$message, context:
A2 := value 2
A1 := value 1"""
	}

	@Test
	void "no context"() {
		def message = "Exception message"
		def ex = new Exception(message)
		def context = new Context(ex)
		assert context.toString() ==
		"""$message"""
	}
}
