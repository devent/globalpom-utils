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
import org.perfidix.Benchmark
import org.perfidix.annotation.BeforeBenchClass
import org.perfidix.annotation.Bench
import org.perfidix.ouput.TabularSummaryOutput

import com.anrisoftware.globalpom.distribution.api.Distribution
import com.anrisoftware.globalpom.distribution.core.DistributionCoreModule
import com.anrisoftware.globalpom.distribution.range.RangeFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Benchmark of linear distribution of values.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
@Slf4j
class LinearDistributionBenchTest {

    @Bench(runs = 32)
    void "linear distribution"() {
        for (int i = 0; i < data.values.length; i++) {
            distribution.add data.values[i]
        }
    }

    @Test
    void runBenchmark() {
        def b = new Benchmark()
        b.add this
        def result = b.run()
        new TabularSummaryOutput().visitBenchmark(result)
    }

    static Injector injector

    static LinearDistributionFactory distributionFactory

    static RangeFactory rangeFactory

    static Map data

    Distribution distribution

    @BeforeClass
    static void createFactory() {
        TestUtils.toStringStyle
        injector = Guice.createInjector(new DistributionCoreModule(), new LinearDistributionModule())
        distributionFactory = injector.getInstance LinearDistributionFactory
        rangeFactory = injector.getInstance RangeFactory
    }

    @BeforeBenchClass
    void beforeBenchmark() {
        data = createData count: 2048, min: -5, max: 5, bins: 256, size: 2048, dec: 2, un: 1
        def range = rangeFactory.create data.min, data.max
        distribution = distributionFactory.create(range, data.bins)
        distribution.reset();
        log.info "Distribution: {}", distribution
    }

    Map createData(Map args) {
        args.values = createValues(args)
        args
    }

    double[] createValues(Map args) {
        List data = []
        int count = args.count
        int min = args.min
        int max = args.max
        def rnd = new Random()
        (1..count).each {
            data << rnd.nextInt(max - min) - max
        }
        return data
    }
}
