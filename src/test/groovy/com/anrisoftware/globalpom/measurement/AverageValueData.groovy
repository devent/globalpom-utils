package com.anrisoftware.globalpom.measurement

import static com.anrisoftware.globalpom.utils.TestUtils.*

/**
 * Average error propagation data.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class AverageValueData {

	List create(AverageValueFactory av, ExactAverageValueFactory ex) {
		[
			[
				name: "add average average",
				func: "f(x,y):=x+y",
				epsilon: 10**-9,
				x: av.create(5, 1, 0.2d, 1),
				y: av.create(6, 2, 0.12d, 2),
				f: { Value x, Value y -> x.add y },
				result: {  Value f ->
					epsilon = 10**-9
					assertDecimalEquals f.value, 5d+6d
					assert !f.exact
					assertDecimalEquals f.uncertainty, 0.32d
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.significant == 1
					assert f.decimal == 1
					assertDecimalEquals f.value, 11d
					assertDecimalEquals f.uncertainty, 0.3d
				},
			],
			[
				name: "add average exact",
				func: "f(x,y):=x+y",
				epsilon: 10**-9,
				x: av.create(5, 1, 0.2d, 1),
				y: ex.create(6),
				f: { x, y -> x.add y },
				result: {  Value f ->
					epsilon = 10**-9
					assertDecimalEquals f.value, 11d
					assert !f.exact
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
				name: "add average double exact",
				func: "f(x,y):=x+y",
				epsilon: 10**-9,
				x: av.create(5, 1, 0.2d, 1),
				y: 6d,
				f: { x, y -> x.add y },
				result: {  Value f ->
					epsilon = 10**-9
					assertDecimalEquals f.value, 11d
					assert !f.exact
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
				name: "add exact average",
				func: "f(x,y):=y+x",
				epsilon: 10**-9,
				x: av.create(5, 1, 0.2d, 1),
				y: ex.create(6),
				f: { Value x, Value y -> y.add x },
				result: {  Value f ->
					epsilon = 10**-9
					assertDecimalEquals f.value, 11d
					assert !f.exact
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
				name: "sub average average",
				func: "f(x,y):=x-y",
				epsilon: 10**-9,
				x: av.create(5, 1, 0.2d, 1),
				y: av.create(6, 2, 0.12d, 2),
				f: { Value x, Value y -> x.sub y },
				result: {  Value f ->
					epsilon = 10**-9
					assertDecimalEquals f.value, -1d
					assert !f.exact
					assertDecimalEquals f.uncertainty, 0.32d
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.significant == 1
					assert f.decimal == 1
					assertDecimalEquals f.value, -1d
					assertDecimalEquals f.uncertainty, 0.3d
				},
			],
			[
				name: "sub average exact",
				func: "f(x,y):=x-y",
				epsilon: 10**-9,
				x: av.create(5, 1, 0.2d, 1),
				y: ex.create(6),
				f: { Value x, Value y -> x.sub y },
				result: {  Value f ->
					epsilon = 10**-9
					assertDecimalEquals f.value, -1d
					assert !f.exact
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
				name: "sub average double exact",
				func: "f(x,y):=x-y",
				epsilon: 10**-9,
				x: av.create(5, 1, 0.2d, 1),
				y: 6d,
				f: { x, y -> x.sub y },
				result: {  Value f ->
					epsilon = 10**-9
					assertDecimalEquals f.value, -1d
					assert !f.exact
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
				name: "log average",
				func: "f(x):=logx",
				epsilon: 10**-9,
				x: av.create(0.5d, 1, 0.2d, 1),
				y: null,
				f: { Value x, Value y -> x.log() },
				result: {  Value f ->
					epsilon = 10**-8
					assertDecimalEquals f.value, -0.69314718d
					assert !f.exact
					assertDecimalEquals f.uncertainty, 0.4d
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.significant == 1
					assert f.decimal == 1
					assertDecimalEquals f.value, -0.7d
					assertDecimalEquals f.uncertainty, 0.4d
				},
			],
			[
				name: "mul exactly log average",
				func: "f(x):=2*logx",
				epsilon: 10**-9,
				x: av.create(0.5d, 1, 0.2d, 1),
				y: ex.create(2d),
				f: { Value x, Value y -> y.mul x.log() },
				result: {  Value f ->
					epsilon = 10**-7
					assertDecimalEquals f.value, -1.3862944d
					assert !f.exact
					assertDecimalEquals f.uncertainty, 0.8d
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.significant == 1
					assert f.decimal == 1
					assertDecimalEquals f.value, -1.4d
					assertDecimalEquals f.uncertainty, 0.8d
				},
			],
			[
				name: "log average mul exactly",
				func: "f(x):=logx*2",
				epsilon: 10**-9,
				x: av.create(0.5d, 1, 0.2d, 1),
				y: ex.create(2d),
				f: { Value x, Value y -> x.log() mul y },
				result: {  Value f ->
					epsilon = 10**-7
					assertDecimalEquals f.value, -1.3862944d
					assert !f.exact
					assertDecimalEquals f.uncertainty, 0.8d
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.significant == 1
					assert f.decimal == 1
					assertDecimalEquals f.value, -1.4d
					assertDecimalEquals f.uncertainty, 0.8d
				},
			],
			[
				name: "log average mul exactly",
				func: "f(x):=logx*2",
				epsilon: 10**-9,
				x: av.create(0.5d, 1, 0.2d, 1),
				y: 2d,
				f: { x, y -> x.log() mul y },
				result: {  Value f ->
					epsilon = 10**-7
					assertDecimalEquals f.value, -1.3862944d
					assert !f.exact
					assertDecimalEquals f.uncertainty, 0.8d
				},
				rounded: {  Value f ->
					f = f.roundedValue
					assert f.significant == 1
					assert f.decimal == 1
					assertDecimalEquals f.value, -1.4d
					assertDecimalEquals f.uncertainty, 0.8d
				},
			],
			[
				name: "exp average",
				func: "f(x):=exp^0.5",
				epsilon: 10**-9,
				x: av.create(0.5d, 1, 0.2d, 1),
				y: null,
				f: { x, y -> x.exp() },
				result: {  Value f ->
					epsilon = 10**-7
					assertDecimalEquals f.value, 1.6487213d
					assert !f.exact
					assertDecimalEquals f.uncertainty, 0.12130613d
				},
				rounded: {  Value f ->
					epsilon = 10**-7
					f = f.roundedValue
					assert f.significant == 1
					assert f.decimal == 1
					assertDecimalEquals f.value, 1.6d
					assertDecimalEquals f.uncertainty, 0.1d
				},
			],
			[
				name: "exp average negative",
				func: "f(x):=exp^-0.5",
				epsilon: 10**-9,
				x: av.create(-0.5d, 1, 0.2d, 1),
				y: null,
				f: { x, y -> x.exp() },
				result: {  Value f ->
					epsilon = 10**-8
					assertDecimalEquals f.value, 0.60653066d
					assert !f.exact
					assertDecimalEquals f.uncertainty, 0.32974425d
				},
				rounded: {  Value f ->
					epsilon = 10**-8
					f = f.roundedValue
					assert f.significant == 1
					assert f.decimal == 1
					assertDecimalEquals f.value, 0.6d
					assertDecimalEquals f.uncertainty, 0.3d
				},
			],
		]
	}
}
