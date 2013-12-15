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
package com.anrisoftware.globalpom.measurement;

import static org.apache.commons.math3.util.FastMath.sqrt;

/**
 * Calculation of error propagation using the standard deviation.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public class StandardValueMath {

	public static double subUncertainty(double sa, double sb) {
		return sqrt(pow2(sa) + pow2(sb));
	}

	public static double mulUncertaintly(double a, double sa, double b,
			double sb, double z) {
		return sqrt(pow2(sa / a) + pow2(sb / b)) * z;
	}

	public static double logUncertainty(double a, double sa) {
		return sa / a;
	}

	public static double expUncertainty(double sa, double z) {
		return sa * z;
	}

    public static double reciprocalUncertaintly(double a, double sa, double z) {
        return z * sa / a;
    }

	private static double pow2(double value) {
		return value * value;
	}

}
