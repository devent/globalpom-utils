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
    public Value getRoundedValue() {
        return value.getRoundedValue();
	}

	@Override
	public Value roundedValue(int sig, int dec) {
        return value.roundedValue(sig, dec);
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
    public Value add(Value addend) {
        return value.add(addend);
	}

	@Override
    public Value add(double addend) {
        return value.add(addend);
	}

	@Override
    public Value sub(Value subtrahend) {
        return value.sub(subtrahend);
	}

	@Override
    public Value sub(double subtrahend) {
        return value.sub(subtrahend);
	}

	@Override
    public Value mul(Value factor) {
        return value.mul(factor);
	}

	@Override
    public Value mul(double factor) {
        return value.mul(factor);
	}

	@Override
    public Value div(Value divisor) {
        return value.div(divisor);
	}

	@Override
    public Value div(double divisor) {
        return value.div(divisor);
	}

	@Override
    public Value reciprocal() {
        return value.reciprocal();
    }

    @Override
    public Value log() {
        return value.log();
	}

	@Override
    public Value exp() {
        return value.exp();
	}

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
