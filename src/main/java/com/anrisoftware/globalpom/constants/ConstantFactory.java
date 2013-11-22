package com.anrisoftware.globalpom.constants;

import javax.measure.unit.Unit;

import com.anrisoftware.globalpom.measurement.Value;

/**
 * Factory to create physical constant.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface ConstantFactory {

	/**
	 * Creates the physical constant with the specified value and unit.
	 * 
	 * @param value
	 *            the {@link Value}.
	 * 
	 * @param unit
	 *            the {@link Unit}.
	 * 
	 * @return the {@link Constant}.
	 */
	@SuppressWarnings("rawtypes")
	Constant create(Value value, Unit<?> unit);
}
