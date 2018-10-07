/*-
 * #%L
 * Global POM Utilities :: Math
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
package com.anrisoftware.globalpom.math.measurement

import static com.anrisoftware.globalpom.utils.TestUtils.*

import com.anrisoftware.globalpom.math.format.measurement.ValueFormat
import com.anrisoftware.globalpom.math.measurement.Value
import com.anrisoftware.globalpom.math.measurement.ValueFactory

import groovy.transform.CompileStatic

/**
 * Standard error propagation data.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@CompileStatic
class StandardValueData {

    List create(ValueFormat format, ValueFactory v) {
        [
            [
                name: "add",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: v.create(2, 1, 1, 0),
                y: v.create(3, 1, 1, 0),
                f: { Value x, Value y -> x.add y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 2.0d+3.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 5d
                },
            ],
            [
                name: "add",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: v.create(2, 1, 1, 0, (double)0.1),
                y: v.create(3, 1, 1, 0, (double)0.1),
                f: { Value x, Value y -> x.add y },
                result: {  Value f ->
                    assert f.exact == false
                    assertDecimalEquals f.uncertainty, 0.1
                    assertDecimalEquals f.value, 2.0d+3.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 5d
                },
            ],
            [
                name: "add",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: v.create(3, 1, 1, 0),
                f: { Value x, Value y -> x.add y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 4.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 4.0d
                },
            ],
            [
                name: "add",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: v.create(2, 0, 1, -1),
                f: { Value x, Value y -> x.add y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.7d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.7d
                },
            ],
            [
                name: "add",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: 0.2d,
                f: { Value x, double y -> x.add y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.7d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.7d
                },
            ],
            // sub
            [
                name: "sub",
                func: "f(x,y):=x-y",
                epsilon: 10**-8,
                x: v.create(2, 1, 1, 0),
                y: v.create(3, 1, 1, 0),
                f: { Value x, Value y -> x.sub y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, -1.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == -1.0d
                },
            ],
            [
                name: "sub",
                func: "f(x,y):=x-y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: v.create(3, 1, 1, 0),
                f: { Value x, Value y -> x.sub y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, -2.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == -2.0d
                },
            ],
            [
                name: "sub",
                func: "f(x,y):=x-y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: v.create(2, 0, 1, -1),
                f: { Value x, Value y -> x.sub y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.3d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.3d
                },
            ],
            [
                name: "sub",
                func: "f(x,y):=x-y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: 0.2d,
                f: { Value x, double y -> x.sub y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.3d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.3d
                },
            ],
            // mul
            [
                name: "mul",
                func: "f(x,y):=x*y",
                epsilon: 10**-8,
                x: v.create(2, 1, 1, 0),
                y: v.create(3, 1, 1, 0),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 6.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 6.0d
                },
            ],
            [
                name: "mul",
                func: "f(x,y):=x*y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: v.create(3, 1, 1, 0),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 2.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 2.0d
                },
            ],
            [
                name: "mul",
                func: "f(x,y):=x*y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: v.create(2, 0, 1, -1),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.1d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.1d
                },
            ],
            [
                name: "mul",
                func: "f(x,y):=x*y",
                epsilon: 10**-26,
                x: format.parse("1.672621777E-27(0.000000074E-27)"),
                y: format.parse("299792458.000000000"),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assertDecimalEquals f.value, 5.0143939E-019
                    assertDecimalEquals f.uncertainty, 2.2E-026
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assertDecimalEquals s, 5.0143939E-019
                },
            ],
            // div
            [
                name: "div",
                func: "f(x,y):=x/y",
                epsilon: 10**-8,
                x: v.create(2, 1, 1, 0),
                y: v.create(3, 1, 1, 0),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.7d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.7d
                },
            ],
            [
                name: "div",
                func: "f(x,y):=x/y",
                epsilon: 10**-8,
                x: v.create(-2630500, 3, 7, -4),
                y: v.create(108881983, 14, 9, 5, (double)6200000.0),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assertDecimalEquals f.uncertainty, -1.37568167E-11d
                    assertDecimalEquals f.value, -2.41591853E-17d
                },
                rounded: {  Value f ->
                    assertDecimalEquals f.uncertainty, -1.37568167E-11d
                    assertDecimalEquals f.value, -2.41591853E-17d
                },
            ],
            [
                name: "div",
                func: "f(x,y):=x/y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: v.create(3, 1, 1, 0),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.2d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.2d
                },
            ],
            [
                name: "div",
                func: "f(x,y):=x/y",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: v.create(2, 0, 1, -1),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 3.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 3.0d
                },
            ],
            [
                name: "div unc/unc",
                func: "f(x,y):=x/y",
                epsilon: 10**-23,
                x: format.parse("1.054571726E-34(0.000000047E-34)"),
                y: format.parse("5.0143939E-019(2.2E-026)"),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assertDecimalEquals f.value, 2.1030891E-016
                    assertDecimalEquals f.uncertainty, 1.3E-023
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assertDecimalEquals s, 2.1030891E-016
                },
            ],
            [
                name: "div const/unc",
                func: "f(x,y):=x/y",
                epsilon: 10**15,
                x: format.parse("299792458.000000000"),
                y: format.parse("2.103089105E-16(0.000000132E-16)"),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assertDecimalEquals f.value, 1.42548624E+024
                    assertDecimalEquals f.uncertainty, 8.9E+016
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assertDecimalEquals s, 1.42548624E+024
                },
            ],
            [
                name: "div unc/const",
                func: "f(x,y):=x/y",
                epsilon: 10**-34,
                x: format.parse("7.015150143E-25(0.000000441E-25)"),
                y: 60.0,
                f: { Value x, double y -> x.div y },
                result: {  Value f ->
                    assertDecimalEquals f.value, 1.169191690E-26
                    assertDecimalEquals f.uncertainty, 7.4E-034
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assertDecimalEquals s, 1.169191690E-26
                },
            ],
            [
                name: "div unc/unc",
                func: "f(x,y):=x/y",
                epsilon: 10**5,
                x: format.parse("1.503277490E-10(6.700000000E-18)"),
                y: format.parse("1.38064852E-23(0.00000079E-23)"),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assertDecimalEquals f.value, 1.088819830E+13
                    assertDecimalEquals f.uncertainty, 6.200000000E+06
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assertDecimalEquals s, 1.088819830E+13
                },
            ],
            // reciprocal
            [
                name: "reciprocal",
                func: "f(x):=1/x",
                epsilon: 10**-8,
                x: v.create(2, 1, 1, 0),
                y: null,
                f: { Value x, Value y -> x.reciprocal() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.5d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.5d
                },
            ],
            [
                name: "reciprocal",
                func: "f(x):=1/x",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: null,
                f: { Value x, Value y -> x.reciprocal() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 2.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 2.0d
                },
            ],
            [
                name: "reciprocal",
                func: "f(x):=1/x",
                epsilon: 10**-8,
                x: v.create(5, 2, 1, 1),
                y: null,
                f: { Value x, Value y -> x.reciprocal() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.02d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.02d
                },
            ],
            [
                name: "reciprocal unc",
                func: "f(x):=1/x",
                epsilon: 10**-32,
                x: format.parse("1.425486240E24(0.000000090E24)"),
                y: null,
                f: { Value x, Value y -> x.reciprocal() },
                result: {  Value f ->
                    assertDecimalEquals f.value, 7.0151501E-025
                    assertDecimalEquals f.uncertainty, 4.4E-032
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assertDecimalEquals s, 7.0151501E-025
                },
            ],
            // log
            [
                name: "log",
                func: "f(x):=log(x)",
                epsilon: 10**-8,
                x: v.create(2, 1, 1, 0),
                y: null,
                f: { Value x, Value y -> x.log() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.7d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.7d
                },
            ],
            [
                name: "log",
                func: "f(x):=log(x)",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: null,
                f: { Value x, Value y -> x.log() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, -0.7d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == -0.7d
                },
            ],
            [
                name: "log",
                func: "f(x):=log(x)",
                epsilon: 10**-8,
                x: v.create(5, 2, 1, 1),
                y: null,
                f: { Value x, Value y -> x.log() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 4.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 4.0d
                },
            ],
            // exp
            [
                name: "exp",
                func: "f(x):=exp(x)",
                epsilon: 10**-8,
                x: v.create(2, 1, 1, 0),
                y: null,
                f: { Value x, Value y -> x.exp() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 7.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 7.0d
                },
            ],
            [
                name: "exp",
                func: "f(x):=exp(x)",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: null,
                f: { Value x, Value y -> x.exp() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 2.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 2.0d
                },
            ],
            [
                name: "exp",
                func: "f(x):=exp(x)",
                epsilon: 10**-8,
                x: v.create(5, 2, 1, 1),
                y: null,
                f: { Value x, Value y -> x.exp() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 5.0E21d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    //assert s == 5.0E21d
                },
            ],
            // abs
            [
                name: "abs",
                func: "f(x):=abs(x)",
                epsilon: 10**-8,
                x: v.create(2, 1, 1, 0),
                y: null,
                f: { Value x, Value y -> x.abs() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 2.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 2.0d
                },
            ],
            [
                name: "abs",
                func: "f(x):=abs(x)",
                epsilon: 10**-8,
                x: v.create(5, 0, 1, -1),
                y: null,
                f: { Value x, Value y -> x.abs() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.5d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.5d
                },
            ],
            [
                name: "abs",
                func: "f(x):=abs(x)",
                epsilon: 10**-8,
                x: v.create(5, 2, 1, 1),
                y: null,
                f: { Value x, Value y -> x.abs() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 50.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 50.0d
                },
            ],
        ]
    }
}
