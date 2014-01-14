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
package com.anrisoftware.globalpom.measurement

import static com.anrisoftware.globalpom.utils.TestUtils.*

import javax.measure.unit.SI

/**
 * Standard error propagation data.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class StandardMeasureData {

    List create(StandardMeasureFactory m, StandardValueFactory v, ExactStandardValueFactory ex) {
        [
            [
                name: "add uncertain uncertain",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: m.create(v.create(6.00d, 2, 0.12d, 2), SI.METER),
                f: { Value x, Value y -> x.add y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 5.0d+6.00d
                    assertDecimalEquals f.uncertainty, 0.23323808d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 11d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
            ],
            [
                name: "add uncertain exact",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: ex.create(6),
                f: { x, y -> x.add y },
                result: { Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 11d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 11d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
            ],
            [
                name: "add exact uncertain",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: ex.create(6),
                y: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                f: { Value x, Value y -> y.add x },
                result: { Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 11d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 11d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
            ],
            [
                name: "sub uncertain uncertain",
                func: "f(x,y):=x-y",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: m.create(v.create(6.00d, 2, 0.12d, 2), SI.METER),
                f: { Value x, Value y -> x.sub y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, -1d
                    assertDecimalEquals f.uncertainty, 0.23323808d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, -1d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
            ],
            [
                name: "sub uncertain exact",
                func: "f(x,y):=x-y",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: ex.create(6),
                f: { Value x, Value y -> x.sub y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, -1d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, -1d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
            ],
            [
                name: "sub exact uncertain",
                func: "f(x,y):=x-y",
                epsilon: 10**-8,
                x: ex.create(6),
                y: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                f: { Value x, Value y -> x.sub y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 1d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 1d
                    assertDecimalEquals f.uncertainty, 0.2d
                },
            ],
            [
                name: "mul uncertain uncertain",
                func: "f(x,y):=x*y",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: m.create(v.create(6.00d, 2, 0.12d, 2), SI.METER),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.34164079d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.significant == 2
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.3d
                },
            ],
            [
                name: "mul uncertain exact",
                func: "f(x,y):=x*y",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: ex.create(6),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.20000000d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 2
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.2d
                },
            ],
            [
                name: "mul exact uncertain",
                func: "f(x,y):=x-y",
                epsilon: 10**-8,
                x: ex.create(6),
                y: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.20000000d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 2
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.2d
                },
            ],
            [
                name: "div uncertain uncertain",
                func: "f(x,y):=x/y",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: m.create(v.create(6.00d, 2, 0.12d, 2), SI.METER),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 0.8333333333d
                    assertDecimalEquals f.uncertainty, 0.03726780d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 0.8d
                    assertDecimalEquals f.uncertainty, 0.04d
                },
            ],
            [
                name: "div uncertain exact",
                func: "f(x,y):=x/y",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: ex.create(6),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 0.8333333333d
                    assertDecimalEquals f.uncertainty, 0.03333333d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 0.8d
                    assertDecimalEquals f.uncertainty, 0.03d
                },
            ],
            [
                name: "div exact uncertain",
                func: "f(x,y):=x/y",
                epsilon: 10**-8,
                x: ex.create(6),
                y: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 1.2d
                    assertDecimalEquals f.uncertainty, 0.03333333d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 1.2d
                    assertDecimalEquals f.uncertainty, 0.03d
                },
            ],
            [
                name: "reciprocal uncertain",
                func: "f(x):=1/x",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: null,
                f: { Value x, Value y -> x.reciprocal() },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 0.2d
                    assertDecimalEquals f.uncertainty, 0.008d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 0.2d
                    assertDecimalEquals f.uncertainty, 0.008d
                },
            ],
            [
                name: "log uncertain",
                func: "f(x):=log(x)",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: null,
                f: { Value x, Value y -> x.log() },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 1.6094379124d
                    assertDecimalEquals f.uncertainty, 0.04000000d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 1.6d
                    assertDecimalEquals f.uncertainty, 0.04d
                },
            ],
            [
                name: "mul exactly log uncertain",
                func: "f(x):=y*log(x)",
                epsilon: 10**-7,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: ex.create(2.0d),
                f: { Value x, Value y -> y.mul x.log() },
                result: { Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 3.2188758d
                    assertDecimalEquals f.uncertainty, 0.08000000d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 3.2d
                    assertDecimalEquals f.uncertainty, 0.08d
                },
            ],
            [
                name: "log uncertain mul exactly",
                func: "f(x):=log(x)*y",
                epsilon: 10**-7,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: ex.create(2.0d),
                f: { Value x, Value y -> x.log() mul y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 3.2188758d
                    assertDecimalEquals f.uncertainty, 0.08000000d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 3.2d
                    assertDecimalEquals f.uncertainty, 0.08d
                },
            ],
            [
                name: "exp uncertain",
                func: "f(x):=e^x",
                epsilon: 10**-8,
                x: m.create(v.create(5.0d, 1, 0.2d, 1), SI.METER),
                y: null,
                f: { x, y -> x.exp() },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 148.4131591026d
                    assertDecimalEquals f.uncertainty, 29.68263182d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 2
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 148.4d
                    assertDecimalEquals f.uncertainty, 30.0d
                },
            ],
            [
                name: "exp uncertain negative",
                func: "f(x):=e^-0.5",
                epsilon: 10**-8,
                x: m.create(v.create(-0.5d, 1, 0.2d, 1), SI.METER),
                y: null,
                f: { x, y -> x.exp() },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 0.6065306597d
                    assertDecimalEquals f.uncertainty, 0.12130613d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 0.6d
                    assertDecimalEquals f.uncertainty, 0.1d
                },
            ],
        ]
    }
}
