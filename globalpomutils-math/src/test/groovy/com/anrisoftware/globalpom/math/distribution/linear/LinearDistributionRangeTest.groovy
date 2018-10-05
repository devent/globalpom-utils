/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.math.distribution.linear

import static com.anrisoftware.globalpom.utils.TestUtils.*

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.math.distribution.core.DistributionCoreModule
import com.anrisoftware.globalpom.math.distribution.linear.LinearDistributionModule
import com.anrisoftware.globalpom.math.distribution.linear.LinearDistributionRangeFactory
import com.anrisoftware.globalpom.math.distribution.range.DistributionRange
import com.anrisoftware.globalpom.math.distribution.range.RangeFactory
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

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

    @BeforeEachClass
    static void createFactory() {
        toStringStyle
        injector = Guice.createInjector(new DistributionCoreModule(), new LinearDistributionModule())
        factory = injector.getInstance LinearDistributionRangeFactory
        rangeFactory = injector.getInstance RangeFactory
    }
}
