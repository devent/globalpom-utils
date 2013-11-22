package com.anrisoftware.globalpom.measurement;

import static org.apache.commons.lang3.StringUtils.repeat;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Formats a value to string.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class ValueToString implements Serializable {

	/**
	 * Formats the specified point.
	 * <p>
	 * The format follows the pattern:
	 * 
	 * <pre>
	 * &lt;value&gt;[(&lt;uncertainty&gt;);&lt;significant&gt;;&lt;decimal&gt;;]
	 * </pre>
	 * 
	 * <p>
	 * <h2>Examples</h2>
	 * <p>
	 * <ul>
	 * <li>exact value: {@code 0.0123}
	 * <li>uncertain value: {@code 5.0(0.2);1;1;}
	 * </ul>
	 * 
	 * @param buff
	 *            the {@link StringBuffer} buffer to append the formatted value.
	 * 
	 * @param value
	 *            the {@link Value}.
	 * 
	 * @param format
	 *            the {@link NumberFormat} to format the exact values.
	 */
	public StringBuffer format(StringBuffer buff, Value value,
			NumberFormat format) {
		if (value.isExact()) {
			formatExactValue(buff, value, format);
		} else {
			formatValue(buff, value, format);
		}
		return buff;
	}

	private void formatExactValue(StringBuffer buff, Value value,
			NumberFormat format) {
		buff.append(format.format(value.getValue()));
	}

	private void formatValue(StringBuffer buff, Value value, NumberFormat format) {
		value = value.getRoundedValue();
		double u = value.getUncertainty();
		int sig = value.getSignificant();
		int dec = value.getDecimal();
		String pattern = "0." + repeat('0', dec);
		NumberFormat valueFormat = new DecimalFormat(pattern);
		buff.append(valueFormat.format(value.getValue()));
		buff.append("(").append(u).append(");").append(sig).append(";")
				.append(dec).append(";");
	}

}
