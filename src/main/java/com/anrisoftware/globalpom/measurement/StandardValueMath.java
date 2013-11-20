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

	private static double pow2(double value) {
		return value * value;
	}

}
