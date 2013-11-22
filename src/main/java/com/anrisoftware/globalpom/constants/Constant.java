package com.anrisoftware.globalpom.constants;

import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

import com.anrisoftware.globalpom.measurement.Value;

/**
 * Physical constant.
 * 
 * @param <Q>
 *            the {@link Quantity} type.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface Constant<Q extends Quantity> extends Value {

	/**
	 * The physical unit of the constant.
	 * 
	 * @return the {@link Unit}.
	 */
	Unit<Q> getUnit();

	@Override
	Constant<Q> getRoundedValue();

	@Override
	Constant<Q> add(Value addend);

	@Override
	Constant<Q> add(double addend);

	@Override
	Constant<Q> sub(Value subtrahend);

	@Override
	Constant<Q> sub(double subtrahend);

	@Override
	Constant<Q> mul(Value factor);

	@Override
	Constant<Q> mul(double factor);

	@Override
	Constant<Q> div(Value divisor);

	@Override
	Constant<Q> div(double divisor);

	@Override
	Constant<Q> log();

	@Override
	Constant<Q> exp();
}
