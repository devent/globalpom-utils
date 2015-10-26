/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-math.
 *
 * globalpomutils-math is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-math is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-math. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format

import groovy.util.logging.Slf4j

import java.text.DecimalFormatSymbols
import java.text.ParsePosition

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.format.measurement.AbstractValueParserWorker
import com.anrisoftware.globalpom.format.measurement.ValueParserWorkerFactory
import com.anrisoftware.globalpom.measurement.ValueFactory
import com.anrisoftware.globalpom.utils.TestUtils

/**
 * @see ValueParserWorkerFactory
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
class ValueParserWorkerFactoryTest {

    @Test
    void "parser worker"() {
        List cases = [
            // simple number
            [input: "12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "12", exponentStr: null, uncString: null, negative: false],
            [input: "12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "12", exponentStr: null, uncString: null, negative: false],
            [input: "-12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "-12", exponentStr: null, uncString: null, negative: true],
            [input: "-12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "-12", exponentStr: null, uncString: null, negative: true],
            [input: "12.12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "12.12", exponentStr: null, uncString: null, negative: false],
            // 5
            [input: "12,12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "12,12", exponentStr: null, uncString: null, negative: false],
            [input: "-12.12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "-12.12", exponentStr: null, uncString: null, negative: true],
            [input: "-12,12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "-12,12", exponentStr: null, uncString: null, negative: true],
            [input: "12.12E12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "12.12", exponentStr: "E12", uncString: null, negative: false],
            [input: "12,12E12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "12,12", exponentStr: "E12", uncString: null, negative: false],
            // 10
            [input: "-12.12E12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "-12.12", exponentStr: "E12", uncString: null, negative: true],
            [input: "-12,12E12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "-12,12", exponentStr: "E12", uncString: null, negative: true],
            [input: "12.12e12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "12.12", exponentStr: "E12", uncString: null, negative: false],
            [input: "12,12e12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "12,12", exponentStr: "E12", uncString: null, negative: false],
            [input: "-12.12e12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "-12.12", exponentStr: "E12", uncString: null, negative: true],
            // 15
            [input: "-12,12e12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "-12,12", exponentStr: "E12", uncString: null, negative: true],
            [input: "12.12E-12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "12.12", exponentStr: "E-12", uncString: null, negative: false],
            [input: "12,12E-12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "12,12", exponentStr: "E-12", uncString: null, negative: false],
            [input: "-12.12E-12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "-12.12", exponentStr: "E-12", uncString: null, negative: true],
            [input: "-12,12E-12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "-12,12", exponentStr: "E-12", uncString: null, negative: true],
            // 20
            [input: "12.12e-12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "12.12", exponentStr: "E-12", uncString: null, negative: false],
            [input: "12,12e-12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "12,12", exponentStr: "E-12", uncString: null, negative: false],
            [input: "-12.12e-12", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "-12.12", exponentStr: "E-12", uncString: null, negative: true],
            [input: "-12,12e-12", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "-12,12", exponentStr: "E-12", uncString: null, negative: true],
            // uncertainty
            [input: "12(0.5)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            // 25
            [input: "12(0,5)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "-12(0.5)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12(0,5)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "12.12(0.05)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "12,12(0,05)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            // 30
            [input: "-12.12(0.05)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12,12(0,05)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "12.12E12(0.05E12)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "12,12E12(0,05E12)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "-12.12E12(0.05E12)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            // 35
            [input: "-12,12E12(0,05E12)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "12.12e12(0.05e12)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "12,12e12(0,05e12)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "-12.12e12(0.05e12)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12,12e12(0,05e12)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            // 40
            [input: "12.12E-12(0.05E-12)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "12,12E-12(0,05E-12)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "-12.12E-12(0.05E-12)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12,12E-12(0,05E-12)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "12.12e-12(0.05e-12)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            // 45
            [input: "12,12e-12(0,05e-12)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "-12.12e-12(0.05e-12)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12,12e-12(0,05e-12)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            // uncertainty percent
            [input: "12(1%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "12(1%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            // 50
            [input: "-12(0.5%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12(0,5%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "12.12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "12,12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "-12.12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            // 55
            [input: "-12,12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "12.12E12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "12,12E12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "-12.12E12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12,12E12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            // 60
            [input: "12.12e12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "12,12e12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "-12.12e12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12,12e12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "12.12E-12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            // 65
            [input: "12,12E-12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "-12.12E-12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12,12E-12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            [input: "12.12e-12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "12,12e-12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            // 70
            [input: "-12.12e-12(0.05%)", locale: Locale.US, name: "SimpleNumberValueParserWorker"],
            [input: "-12,12e-12(0,05%)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker"],
            // uncertainty simple
            [input: "12(5)", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "12", exponentStr: null, uncString: "(5)", negative: false],
            [input: "12(5)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "12", exponentStr: null, uncString: "(5)", negative: false],
            [input: "-12(5)", locale: Locale.US, name: "SimpleNumberValueParserWorker", valueStr: "-12", exponentStr: null, uncString: "(5)", negative: true],
            // 75
            [input: "-12(5)", locale: Locale.GERMAN, name: "SimpleNumberValueParserWorker", valueStr: "-12", exponentStr: null, uncString: "(5)", negative: true],
            [input: "12.12(5)", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "12.12", exponentStr: null, uncString: "(5)", negative: false],
            [input: "12,12(5)", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "12,12", exponentStr: null, uncString: "(5)", negative: false],
            [input: "-12.12(5)", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "-12.12", exponentStr: null, uncString: "(5)", negative: true],
            [input: "-12,12(5)", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "-12,12", exponentStr: null, uncString: "(5)", negative: true],
            // 80
            [input: "12.12(5)E12", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "12.12", exponentStr: "E12", uncString: "(5)", negative: false],
            [input: "12,12(5)E12", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "12,12", exponentStr: "E12", uncString: "(5)", negative: false],
            [input: "-12.12(5)E12", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "-12.12", exponentStr: "E12", uncString: "(5)", negative: true],
            [input: "-12,12(5)E12", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "-12,12", exponentStr: "E12", uncString: "(5)", negative: true],
            [input: "12.12(5)e12", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "12.12", exponentStr: "E12", uncString: "(5)", negative: false],
            // 85
            [input: "12,12(5)e12", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "12,12", exponentStr: "E12", uncString: "(5)", negative: false],
            [input: "-12.12(5)e12", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "-12.12", exponentStr: "E12", uncString: "(5)", negative: true],
            [input: "-12,12(5)e12", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "-12,12", exponentStr: "E12", uncString: "(5)", negative: true],
            [input: "12.12(5)E-12", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "12.12", exponentStr: "E-12", uncString: "(5)", negative: false],
            [input: "12,12(5)E-12", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "12,12", exponentStr: "E-12", uncString: "(5)", negative: false],
            // 90
            [input: "-12.12(5)E-12", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "-12.12", exponentStr: "E-12", uncString: "(5)", negative: true],
            [input: "-12,12(5)E-12", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "-12,12", exponentStr: "E-12", uncString: "(5)", negative: true],
            [input: "12.12(5)e-12", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "12.12", exponentStr: "E-12", uncString: "(5)", negative: false],
            [input: "12,12(5)e-12", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "12,12", exponentStr: "E-12", uncString: "(5)", negative: false],
            [input: "-12.12(5)e-12", locale: Locale.US, name: "UncertainShortNumberValueParserWorker", valueStr: "-12.12", exponentStr: "E-12", uncString: "(5)", negative: true],
            // 95
            [input: "-12,12(5)e-12", locale: Locale.GERMAN, name: "UncertainShortNumberValueParserWorker", valueStr: "-12,12", exponentStr: "E-12", uncString: "(5)", negative: true],
        ]
        cases.eachWithIndex { Map test, int k ->
            log.info "{}. test case: {}", k, test
            def position = new ParsePosition(0)
            Locale locale = test.locale as Locale
            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale)
            Integer significant = null
            Integer decimal = null
            ValueFactory valueFactory = null
            def workerFactory = new ValueParserWorkerFactory(symbols, locale, significant, decimal, valueFactory)
            def worker = workerFactory.getValueParserWorker(test.input as String, position)
            assert worker.getClass().simpleName == test.name
            if (worker instanceof AbstractValueParserWorker) {
                AbstractValueParserWorker aworker = worker as AbstractValueParserWorker
                if (test.valueStr) {
                    assert aworker.valueStr == test.valueStr
                    assert aworker.exponentStr == test.exponentStr
                    assert aworker.uncString == test.uncString
                    assert aworker.negative == test.negative
                }
            }
        }
    }

    @BeforeClass
    static void createFactories() {
        TestUtils.toStringStyle
    }
}
