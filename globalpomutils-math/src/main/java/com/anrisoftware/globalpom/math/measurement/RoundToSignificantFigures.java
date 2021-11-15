/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.math.measurement;

/*-
 * #%L
 * Global POM Utilities :: Math
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static java.math.BigDecimal.ROUND_HALF_UP;
import static org.apache.commons.math3.util.FastMath.abs;
import static org.apache.commons.math3.util.FastMath.ceil;
import static org.apache.commons.math3.util.FastMath.log10;
import static org.apache.commons.math3.util.FastMath.pow;
import static org.apache.commons.math3.util.FastMath.round;

import java.math.BigDecimal;

/**
 * Rounds the value to the significant figures.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class RoundToSignificantFigures {

	/**
	 * Rounds the value to the significant figures.
	 * <p>
	 * Algorithm takes from <a href=
	 * "http://stackoverflow.com/questions/202302/rounding-to-an-arbitrary-number-of-significant-digits"
	 * >rounding to an arbitrary number of significant digits
	 * [stackoverflow.com]</a>
	 * 
	 * @param value
	 *            the value.
	 * 
	 * @param significant
	 *            the significant figures.
	 * 
	 * @return the rounded value.
	 */
	public static double roundToSignificant(double value, int significant) {
		if (value == 0) {
			return 0;
		}
		double d = ceil(log10(abs(value)));
		int power = significant - (int) d;
		double magnitude = pow(10, power);
		long shifted = round(value * magnitude);
		return shifted / magnitude;
	}

	/**
	 * Rounds the value to the decimal places.
	 * <p>
	 * Algorithm takes from <a href=
	 * "http://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java"
	 * >How to round a number to n decimal places in Java
	 * [stackoverflow.com]</a>
	 * 
	 * @param value
	 *            the value.
	 * 
	 * @param decimal
	 *            the decimal places.
	 * 
	 * @return the rounded value.
	 */
	public static double roundToDecimal(double value, int decimal) {
		return BigDecimal.valueOf(value).setScale(decimal, ROUND_HALF_UP)
				.doubleValue();
	}
}
