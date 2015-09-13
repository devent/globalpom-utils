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
package com.anrisoftware.globalpom.measurement

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.transform.CompileStatic

/**
 * Standard error propagation data.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@CompileStatic
class StandardValueData {

    List create(ValueFactory v) {
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
                x: v.create(5, 0, 1, -1),
                y: v.create(3, 1, 1, 0),
                f: { Value x, Value y -> x.add y },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 3.5d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 3.5d
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
                    assertDecimalEquals f.value, -2.5d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == -2.5d
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
                    assertDecimalEquals f.value, 1.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 1.0d
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
                    assertDecimalEquals f.value, 0.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.0d
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
                    assertDecimalEquals f.value, 0.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 0.0d
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
                    assertDecimalEquals f.value, 1.0d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 1.0d
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
                    assertDecimalEquals f.value, 1.6d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 1.6d
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
                    assertDecimalEquals f.value, 5.184705528587072E21d
                },
                rounded: {  Value f ->
                    double s = f.roundedValue
                    assert s == 5.184705528587072E21d
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
