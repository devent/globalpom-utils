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

import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.globalpom.distribution.core.DistributionCoreModule
import com.anrisoftware.globalpom.distribution.range.RangeFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

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

    @BeforeClass
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
