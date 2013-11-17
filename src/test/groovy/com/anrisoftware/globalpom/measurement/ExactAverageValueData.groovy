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
 * Exact values data.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class ExactAverageValueData {

	List create(AverageValueFactory av, ExactAverageValueFactory ex) {
		[
			[
				name: "add exact exact",
				func: "f(x,y):=x+y",
				epsilon: 10**-9,
				x: ex.create(5),
				y: ex.create(6),
				f: { Value x, Value y -> x.add y },
				result: {  Value f ->
					assertDecimalEquals f.value, 11d
					assert f.exact
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.exact
					assertDecimalEquals f.value, 11d
				},
			],
			[
				name: "add exact double exact",
				func: "f(x,y):=x+y",
				epsilon: 10**-9,
				x: ex.create(5),
				y: 6d,
				f: { x, y -> x.add y },
				result: {  Value f ->
					assertDecimalEquals f.value, 11d
					assert f.exact
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.exact
					assertDecimalEquals f.value, 11d
				},
			],
			[
				name: "log exact",
				func: "f(x,y):=logx",
				epsilon: 10**-8,
				x: ex.create(0.5d),
				y: null,
				f: { Value x, Value y -> x.log() },
				result: {  Value f ->
					assertDecimalEquals f.value, -0.69314718d
					assert f.exact
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.exact
					assertDecimalEquals f.value, -0.69314718d
				},
			],
			[
				name: "exp exact",
				func: "f(x):=e^x",
				epsilon: 10**-7,
				x: ex.create(0.5d),
				y: null,
				f: { Value x, Value y -> x.exp() },
				result: {  Value f ->
					assertDecimalEquals f.value, 1.6487213d
					assert f.exact
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.exact
					assertDecimalEquals f.value, 1.6487213d
				},
			],
		]
	}
}
