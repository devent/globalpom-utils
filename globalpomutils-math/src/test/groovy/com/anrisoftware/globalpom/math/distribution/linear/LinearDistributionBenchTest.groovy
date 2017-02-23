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
import org.perfidix.Benchmark
import org.perfidix.annotation.BeforeBenchClass
import org.perfidix.annotation.Bench
import org.perfidix.ouput.TabularSummaryOutput

import com.anrisoftware.globalpom.math.distribution.api.Distribution
import com.anrisoftware.globalpom.math.distribution.core.DistributionCoreModule
import com.anrisoftware.globalpom.math.distribution.linear.LinearDistributionFactory
import com.anrisoftware.globalpom.math.distribution.linear.LinearDistributionModule
import com.anrisoftware.globalpom.math.distribution.range.RangeFactory
import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

import groovy.util.logging.Slf4j

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
        distribution.reset()
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
