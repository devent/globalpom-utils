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
		]
	}
}
