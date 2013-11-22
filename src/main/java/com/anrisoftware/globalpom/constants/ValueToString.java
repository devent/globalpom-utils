package com.anrisoftware.globalpom.constants;

import static org.apache.commons.lang3.StringUtils.lastIndexOf;

import java.text.NumberFormat;

import com.anrisoftware.globalpom.measurement.Value;

/**
 * Formats a physical constant to string.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class ValueToString extends
		com.anrisoftware.globalpom.measurement.ValueToString {

	/**
	 * Formats the specified point.
	 * <p>
	 * The format follows the pattern:
	 * 
	 * <pre>
	 * &lt;value&gt;[(&lt;uncertainty&gt;);&lt;significant&gt;;&lt;decimal&gt;;&lt;unit&gt;;]
	 * </pre>
	 * 
	 * <p>
	 * <h2>Examples</h2>
	 * <p>
	 * <ul>
	 * <li>exact value: {@code 0.0123;m/s}
	 * <li>uncertain value: {@code 5.0(0.2);1;1;m/s;}
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
	public StringBuffer format(StringBuffer buff, Constant<?> constant,
			NumberFormat format) {
		super.format(buff, constant, format);
		if (lastIndexOf(buff, ";") != buff.length() - 1) {
			buff.append(";");
		}
		buff.append(constant.getUnit().toString()).append(";");
		return buff;
	}
}
