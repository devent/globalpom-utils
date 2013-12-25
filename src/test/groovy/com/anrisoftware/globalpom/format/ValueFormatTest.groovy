/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.format.measurement.ValueFormatFactory
import com.anrisoftware.globalpom.format.measurement.ValueFormatModule
import com.anrisoftware.globalpom.measurement.ExactStandardValueFactory
import com.anrisoftware.globalpom.measurement.ExactValueFactory
import com.anrisoftware.globalpom.measurement.MeasurementModule
import com.anrisoftware.globalpom.measurement.StandardValueFactory
import com.anrisoftware.globalpom.measurement.ValueFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see ValueFormat
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@Slf4j
class ValueFormatTest {

	@Test
	void "format duration"() {
		formats.each {
			def str = formatFactory.create(valueFactory, exactValueFactory).format(it.value)
			log.info "Format ${it.value} as '${str}'"
			assertStringContent str, it.format
		}
	}

	@Test
	void "parse duration"() {
		parses.each {
			log.info "Parse '{}'", it.input
			def value = formatFactory.create(valueFactory, exactValueFactory).parse(it.input)
			assert value == it.value
		}
	}

	static formats

	static parses

	static Injector injector

	static ValueFormatFactory formatFactory

	static ExactValueFactory exactValueFactory

	static ValueFactory valueFactory

	@BeforeClass
	static void createFactories() {
		TestUtils.toStringStyle
		injector = Guice.createInjector(new MeasurementModule(), new ValueFormatModule())
		formatFactory = injector.getInstance(ValueFormatFactory)
		exactValueFactory = injector.getInstance(ExactStandardValueFactory)
		valueFactory = injector.getInstance(StandardValueFactory)
		formats  = [
			[format: "5", value: exactValueFactory.create(5.0d)],
			[format: "0.0123", value: exactValueFactory.create(0.0123d)],
			[format: "5.0E00(0.2);1;1;", value: valueFactory.create(5.0d, 1, 0.2d, 1)],
		]
		parses = [
			[input: "5", value: exactValueFactory.create(5.0d)],
			[input: "0.0123", value: exactValueFactory.create(0.0123d)],
			[input: "5.0(0.2);1;1;", value: valueFactory.create(5.0d, 1, 0.2d, 1)],
		]
	}
}