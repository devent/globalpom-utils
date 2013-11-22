package com.anrisoftware.globalpom.constants;

import java.text.DecimalFormat;

import javax.inject.Inject;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.measurement.Value;

/**
 * Physical constant.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public abstract class AbstractConstant<Q extends Quantity> implements
		Constant<Q> {

	private static final DecimalFormat VALUE_FORMAT = new DecimalFormat(
			"#.#########");

	private final Value value;

	private final Unit<Q> unit;

	@Inject
	private ValueToString toString;

	/**
	 * Sets the specified value and unit of the constant.
	 * 
	 * @param value
	 *            the {@link Value}.
	 * 
	 * @param unit
	 *            the {@link Unit}.
	 */
	protected AbstractConstant(Value value, Unit<Q> unit) {
		this.value = value;
		this.unit = unit;
	}

	@Override
	public Unit<Q> getUnit() {
		return unit;
	}

	@Override
	public double getValue() {
		return value.getValue();
	}

	@Override
	public Constant<Q> getRoundedValue() {
		Value result = value.getRoundedValue();
		return createConstant(result, unit);
	}

	@Override
	public int getSignificant() {
		return value.getSignificant();
	}

	@Override
	public int getDecimal() {
		return value.getDecimal();
	}

	@Override
	public double getUncertainty() {
		return value.getUncertainty();
	}

	@Override
	public boolean isExact() {
		return value.isExact();
	}

	@Override
	public Constant<Q> add(Value addend) {
		Value result = value.add(addend);
		return createConstant(result, unit);
	}

	@Override
	public Constant<Q> add(double addend) {
		Value result = value.add(addend);
		return createConstant(result, unit);
	}

	@Override
	public Constant<Q> sub(Value subtrahend) {
		Value result = value.sub(subtrahend);
		return createConstant(result, unit);
	}

	@Override
	public Constant<Q> sub(double subtrahend) {
		Value result = value.sub(subtrahend);
		return createConstant(result, unit);
	}

	@Override
	public Constant<Q> mul(Value factor) {
		Value result = value.mul(factor);
		return createConstant(result, unit);
	}

	@Override
	public Constant<Q> mul(double factor) {
		Value result = value.mul(factor);
		return createConstant(result, unit);
	}

	@Override
	public Constant<Q> div(Value divisor) {
		Value result = value.div(divisor);
		return createConstant(result, unit);
	}

	@Override
	public Constant<Q> div(double divisor) {
		Value result = value.div(divisor);
		return createConstant(result, unit);
	}

	@Override
	public Constant<Q> log() {
		Value result = value.log();
		return createConstant(result, unit);
	}

	@Override
	public Constant<Q> exp() {
		Value result = value.exp();
		return createConstant(result, unit);
	}

	/**
	 * Creates the specified value and unit of the constant.
	 * 
	 * @param value
	 *            the {@link Value}.
	 * 
	 * @param unit
	 *            the {@link Unit}.
	 * 
	 * @return the {@link Constant}.
	 */
	protected abstract Constant<Q> createConstant(Value value, Unit<Q> unit);

	@Override
	public boolean equals(Object obj) {
		return equals(obj, 3.0);
	}

	@Override
	public boolean equals(Object obj, double dev) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Constant)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		Constant<Q> rhs = (Constant<Q>) obj;
		return getUnit().equals(rhs.getUnit()) ? value.equals(obj, dev) : false;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		toString.format(buff, this, VALUE_FORMAT);
		return new ToStringBuilder(this).append(buff.toString()).build();
	}
}
