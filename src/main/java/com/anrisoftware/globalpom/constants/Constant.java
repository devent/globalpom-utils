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
}
