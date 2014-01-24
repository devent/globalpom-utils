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

/**
 * Standard error propagation data.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
class StandardValueData {

    List create(StandardValueFactory v, ExactStandardValueFactory ex) {
        [
            [
                name: "add uncertain uncertain",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: v.create(5.0d, 1, 0.2d, 1),
                y: v.create(6.00d, 2, 0.12d, 2),
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
                name: "add uncertain uncertain",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: v.create(20.12345d, 1, 0.2d, 5),
                y: v.create(20.56732d, 1, 0.2d, 5),
                f: { Value x, Value y -> x.add y },
                result: { Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 40.69077
                    assertDecimalEquals f.uncertainty, 0.2828427124746d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 5
                    assertDecimalEquals f.value, 40.7d
                    assertDecimalEquals f.uncertainty, 0.3d
                },
            ],
            [
                name: "add uncertain exact",
                func: "f(x,y):=x+y",
                epsilon: 10**-8,
                x: v.create(5.0d, 1, 0.2d, 1),
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
                y: v.create(5.0d, 1, 0.2d, 1),
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
                x: v.create(5.0d, 1, 0.2d, 1),
                y: v.create(6.00d, 2, 0.12d, 2),
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
                x: v.create(5.0d, 1, 0.2d, 1),
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
                y: v.create(5.0d, 1, 0.2d, 1),
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
                x: v.create(5.0d, 1, 0.2d, 1),
                y: v.create(6.00d, 2, 0.12d, 2),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.34164079d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.3d
                },
            ],
            [
                name: "mul uncertain exact",
                func: "f(x,y):=x*y",
                epsilon: 10**-8,
                x: v.create(5.0d, 1, 0.2d, 1),
                y: ex.create(6),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.20000000d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
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
                y: v.create(5.0d, 1, 0.2d, 1),
                f: { Value x, Value y -> x.mul y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.20000000d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 30d
                    assertDecimalEquals f.uncertainty, 1.2d
                },
            ],
            [
                name: "div uncertain uncertain",
                func: "f(x,y):=x/y",
                epsilon: 10**-8,
                x: v.create(5.0d, 1, 0.2d, 1),
                y: v.create(6.00d, 2, 0.12d, 2),
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
                x: v.create(5.0d, 1, 0.2d, 1),
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
                y: v.create(5.0d, 1, 0.2d, 1),
                f: { Value x, Value y -> x.div y },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 1.2d
                    assertDecimalEquals f.uncertainty, 0.048d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 1.2d
                    assertDecimalEquals f.uncertainty, 0.05d
                },
            ],
            [
                name: "reciprocal uncertain",
                func: "f(x):=1/x",
                epsilon: 10**-8,
                x: v.create(5.0d, 1, 0.2d, 1),
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
                name: "reciprocal exact",
                func: "f(x):=1/x",
                epsilon: 10**-8,
                x: ex.create(5.0d),
                y: null,
                f: { Value x, Value y -> x.reciprocal() },
                result: {  Value f ->
                    assert f.exact
                    assertDecimalEquals f.value, 0.2d
                },
                rounded: { Value f ->
                    f = f.roundedValue
                    assert f.exact
                    assertDecimalEquals f.value, 0.2d
                },
            ],
            [
                name: "log uncertain",
                func: "f(x):=log(x)",
                epsilon: 10**-8,
                x: v.create(5.0d, 1, 0.2d, 1),
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
                x: v.create(5.0d, 1, 0.2d, 1),
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
                x: v.create(5.0d, 1, 0.2d, 1),
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
                x: v.create(5.0d, 1, 0.2d, 1),
                y: null,
                f: { x, y -> x.exp() },
                result: {  Value f ->
                    assert !f.exact
                    assertDecimalEquals f.value, 148.4131591026d
                    assertDecimalEquals f.uncertainty, 29.68263182d
                },
                rounded: {  Value f ->
                    f = f.roundedValue
                    assert f.significant == 1
                    assert f.decimal == 1
                    assertDecimalEquals f.value, 148.4d
                    assertDecimalEquals f.uncertainty, 30.0d
                },
            ],
            [
                name: "exp uncertain negative",
                func: "f(x):=e^-0.5",
                epsilon: 10**-8,
                x: v.create(-0.5d, 1, 0.2d, 1),
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
            [
                name: "equals uncertain, default deviation",
                func: "f(x, y):=x=y",
                epsilon: 10**-8,
                x: v.create(1.5d, 1, 0.2d, 1),
                y: v.create(1.5d, 1, 0.2d, 1),
                f: { x, y -> x.equals(y) },
                result: {  boolean eq -> assert eq },
                rounded: { },
            ],
            [
                name: "equals uncertain and exact, default deviation",
                func: "f(x, y):=x=y",
                epsilon: 10**-8,
                x: v.create(1.5d, 1, 0.2d, 1),
                y: ex.create(1.5d),
                f: { x, y -> x.equals(y) },
                result: {  boolean eq -> assert eq },
                rounded: { },
            ],
            [
                name: "unequals uncertain, default deviation",
                func: "f(x, y):=x!=y",
                epsilon: 10**-8,
                x: v.create(1.5d, 1, 0.2d, 1),
                y: v.create(2.5d, 1, 0.2d, 1),
                f: { x, y -> x.equals(y) },
                result: {  boolean eq -> assert !eq },
                rounded: { },
            ],
            [
                name: "unequals uncertain and exact, default deviation",
                func: "f(x, y):=x!=y",
                epsilon: 10**-8,
                x: v.create(1.5d, 1, 0.2d, 1),
                y: ex.create(2.5d),
                f: { x, y -> x.equals(y) },
                result: {  boolean eq -> assert !eq },
                rounded: { },
            ],
            [
                name: "compare uncertain, default deviation",
                func: "f(x, y):=x<y",
                epsilon: 10**-8,
                x: v.create(1.5d, 1, 0.2d, 1),
                y: v.create(2.5d, 1, 0.2d, 1),
                f: { x, y -> x.compareTo(y) },
                result: {  int com -> assert com == -1 },
                rounded: { },
            ],
            [
                name: "compare uncertain, default deviation",
                func: "f(x, y):=x>y",
                epsilon: 10**-8,
                x: v.create(2.5d, 1, 0.2d, 1),
                y: v.create(1.5d, 1, 0.2d, 1),
                f: { x, y -> x.compareTo(y) },
                result: {  int com -> assert com == 1 },
                rounded: { },
            ],
            [
                name: "compare uncertain, default deviation",
                func: "f(x, y):=x=y",
                epsilon: 10**-8,
                x: v.create(2.5d, 1, 0.2d, 1),
                y: v.create(2.2d, 1, 0.2d, 1),
                f: { x, y -> x.compareTo(y) },
                result: {  int com -> assert com == 0 },
                rounded: { },
            ],
            [
                name: "compare uncertain and exact, default deviation",
                func: "f(x, y):=x<y",
                epsilon: 10**-8,
                x: v.create(1.5d, 1, 0.2d, 1),
                y: ex.create(2.5d),
                f: { x, y -> x.compareTo(y) },
                result: {  int com -> assert com == -1 },
                rounded: { },
            ],
            [
                name: "compare uncertain and exact, default deviation",
                func: "f(x, y):=x>y",
                epsilon: 10**-8,
                x: v.create(2.5d, 1, 0.2d, 1),
                y: ex.create(1.5d),
                f: { x, y -> x.compareTo(y) },
                result: {  int com -> assert com == 1 },
                rounded: { },
            ],
            [
                name: "uncertain bounds values",
                func: "f(x):=x",
                epsilon: 10**-2,
                x: v.create(1.2d, 1, 0.1d, 1),
                y: null,
                f: { x, y -> x },
                result: { },
                rounded: { f ->
                    f = f.roundedValue
                    assertDecimalEquals f.minValue, 0.89d
                    assertDecimalEquals f.maxValue, 1.50d
                },
            ],
            [
                name: "exact bounds values",
                func: "f(x):=x",
                epsilon: 10**-2,
                x: ex.create(1.2d),
                y: null,
                f: { x, y -> x },
                result: { },
                rounded: { f ->
                    f = f.roundedValue
                    assertDecimalEquals f.minValue, 1.2d
                    assertDecimalEquals f.maxValue, 1.2d
                },
            ],
        ]
    }
}
