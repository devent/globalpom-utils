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
package com.anrisoftware.globalpom.distribution.linear

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.distribution.core.DistributionCoreModule
import com.anrisoftware.globalpom.distribution.range.DistributionRange
import com.anrisoftware.globalpom.distribution.range.RangeFactory
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * @see LinearDistributionRange
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
@Slf4j
class LinearDistributionRangeTest {

    @Test
    void "distribute range"() {
        epsilon = 10e-4
        def range = factory.create rangeFactory.create(data.min, data.max)
        range.iterator(data.size).eachWithIndex { DistributionRange d, int i ->
            log.info "Distribution bin #$i $d"
            assertDecimalEquals d.min, data.ranges[i]
            assertDecimalEquals d.max, data.ranges[i + 1]
        }
    }

    @Test
    void "distribute range 2"() {
        epsilon = 10e-4
        def range = factory.create rangeFactory.create(data2.min, data2.max)
        range.iterator(data2.size).eachWithIndex { DistributionRange d, int i ->
            log.info "Distribution bin #$i $d"
            assertDecimalEquals d.min, data2.ranges[i]
            assertDecimalEquals d.max, data2.ranges[i + 1]
        }
    }

    static Injector injector

    static LinearDistributionRangeFactory factory

    static RangeFactory rangeFactory

    static Map data = new LinearDistributionRangeData().run()

    static Map data2 = new LinearDistributionRangeData2().run()

    @BeforeClass
    static void createFactory() {
        toStringStyle
        injector = Guice.createInjector(new DistributionCoreModule(), new LinearDistributionModule())
        factory = injector.getInstance LinearDistributionRangeFactory
        rangeFactory = injector.getInstance RangeFactory
    }
}
