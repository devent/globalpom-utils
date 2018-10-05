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

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.math.distribution.core.DistributionCoreModule
import com.anrisoftware.globalpom.math.distribution.linear.LinearDistributionFactory
import com.anrisoftware.globalpom.math.distribution.linear.LinearDistributionModule
import com.anrisoftware.globalpom.math.distribution.range.RangeFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

/**
 * Linear distribution of values.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
@Slf4j
class LinearDistributionTest {

    @Test
    void "linear distribution"() {
        def range = rangeFactory.create absDerivation2.min, absDerivation2.max
        def values = absDerivation2.values
        def bins = 50
        def size = values.size()
        int sum = 0
        def distribution = distributionFactory.create(range, bins)
        distribution.reset()
        log.info "Distribution: {}", distribution
        for (int i = 0; i < size; i++) {
            distribution.add values[i]
        }
        def binvalues
        int binsize
        (0..<bins).each { int bin ->
            binvalues = distribution.values(bin)
            binsize = binvalues.size()
            sum = sum + binsize
            log.info "Distribution values #{}: ({}) {}", bin, binsize, binvalues
            assert binvalues.containsAll(absDistributionDer2[bin] as double[])
        }
        assert sum == values.size()
        assert distribution.getMax() == 11
    }

    @Test
    void "simple linear distribution"() {
        def range = rangeFactory.create simpleDataDer2Abs.min, simpleDataDer2Abs.max
        def values = simpleDataDer2Abs.values
        def bins = 20
        def size = values.size()
        int sum = 0
        def distribution = distributionFactory.create(range, bins)
        distribution.reset()
        log.info "Distribution: {}", distribution
        for (int i = 0; i < size; i++) {
            distribution.add values[i]
        }
        def binvalues
        int binsize
        (0..<bins).each { int bin ->
            binvalues = distribution.values(bin)
            binsize = binvalues.size()
            sum = sum + binsize
            log.info "Distribution values #{}: ({}) {}", bin, binsize, binvalues
            assert binvalues.size() == simpleDataDer2AbsDist[bin].size()
            assert binvalues.containsAll(simpleDataDer2AbsDist[bin] as double[])
        }
        assert sum == values.size()
        assert distribution.getMax() == 2
    }

    static Injector injector

    static LinearDistributionFactory distributionFactory

    static RangeFactory rangeFactory

    @BeforeEachClass
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new DistributionCoreModule(), new LinearDistributionModule())
        distributionFactory = injector.getInstance LinearDistributionFactory
        rangeFactory = injector.getInstance RangeFactory
    }

    private Map absDerivation2 = new NoiseDataAbsDerivation2().run()

    private List absDistributionDer2 = new NoiseDataAbsDistributionDer2().run()

    private Map simpleDataDer2Abs = new SimpleDataDer2Abs().run()

    private List simpleDataDer2AbsDist = new SimpleDataDer2AbsDist().run()
}
