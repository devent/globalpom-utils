package com.anrisoftware.globalpom.format

import java.text.Format

import org.junit.Before
import org.junit.Test

import com.anrisoftware.globalpom.format.enums.EnumFormat

/**
 * @see EnumFormat
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class EnumFormatTest {

	static enum TestEnum {
		FOO, BAR
	}

	@Test
	void "parse enum"() {
		inputs.each {
			assert format.parseObject(it.str) == it.item
		}
	}

	@Test
	void "format enum"() {
		inputs.each {
			assert format.format(it.item) == it.format
		}
	}

	Format format

	@Before
	void setupFormat() {
		format = new EnumFormat(TestEnum)
	}

	static inputs = [
		[str: "FOO", format: "FOO", item: TestEnum.FOO],
		[str: "BAR", format: "BAR", item: TestEnum.BAR],
		[str: "foo", format: "FOO", item: TestEnum.FOO],
	]
}
