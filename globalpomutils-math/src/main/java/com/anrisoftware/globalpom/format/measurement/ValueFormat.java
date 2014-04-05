/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-math.
 *
 * globalpomutils-math is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-math is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-math. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format.measurement;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import com.anrisoftware.globalpom.measurement.ExactValueFactory;
import com.anrisoftware.globalpom.measurement.Value;
import com.anrisoftware.globalpom.measurement.ValueFactory;
import com.anrisoftware.globalpom.measurement.ValueToString;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Formats and parses a {@link Value} value.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class ValueFormat extends Format {

	private final Pattern VALUE_PATTERN = Pattern
			.compile("^(.*?)\\((.*?)\\);(\\d+);(\\d+);$");

	private final NumberFormat numberFormat;

	private final ValueFactory valueFactory;

	private final ExactValueFactory exactValueFactory;

	@Inject
	private ValueToString valueToString;

	@Inject
	private ValueFormatLogger log;

	/**
	 * @see ValueFormatFactory#create(ValueFactory, ExactValueFactory)
	 */
	@AssistedInject
	ValueFormat(@Assisted ValueFactory valueFactory,
			@Assisted ExactValueFactory exactValueFactory) {
		this(valueFactory, exactValueFactory, new DecimalFormat("#.#########"));
	}

	/**
	 * @see ValueFormatFactory#create(ValueFactory, ExactValueFactory,
	 *      NumberFormat)
	 */
	@AssistedInject
	ValueFormat(@Assisted ValueFactory valueFactory,
			@Assisted ExactValueFactory exactValueFactory,
			@Assisted NumberFormat format) {
		this.valueFactory = valueFactory;
		this.exactValueFactory = exactValueFactory;
		this.numberFormat = format;
	}

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
	 * @param obj
	 *            the {@link Value}.
	 */
	@Override
	public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
		if (obj instanceof Value) {
			valueToString.format(buff, (Value) obj, numberFormat);
		}
		return buff;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return parse(source, pos);
	}

	/**
	 * Parses the specified string to value.
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
	 * @return the parsed {@link Value}.
	 *
	 * @throws ParseException
	 *             if the string cannot be parsed to a value.
	 */
	public Value parse(String source) throws ParseException {
		ParsePosition pos = new ParsePosition(0);
		Value result = parse(source, pos);
		if (pos.getIndex() == 0) {
			throw log.errorParseValue(source, pos);
		}
		return result;
	}

	/**
	 * @see #parse(String)
	 * 
	 * @param pos
	 *            the index {@link ParsePosition} position from where to start
	 *            parsing.
	 */
	public Value parse(String source, ParsePosition pos) {
		source = source.substring(pos.getIndex());
		try {
			Value address = parseValue(source);
			pos.setErrorIndex(-1);
			pos.setIndex(source.length());
			return address;
		} catch (ParseException e) {
			pos.setIndex(0);
			pos.setErrorIndex(0);
			return null;
		}
	}

	private Value parseValue(String string)
			throws ParseException {
		Matcher matcher = VALUE_PATTERN.matcher(string);
		if (!matcher.matches()) {
			return parseExactValue(string);
		} else {
			return parseUncertainValue(string, matcher);
		}
	}

	private Value parseUncertainValue(String string, Matcher pattern)
			throws ParseException {
		double value = numberFormat.parse(pattern.group(1)).doubleValue();
		double unc = numberFormat.parse(pattern.group(2)).doubleValue();
		int sig = numberFormat.parse(pattern.group(3)).intValue();
		int dec = numberFormat.parse(pattern.group(4)).intValue();
		return valueFactory.create(value, sig, unc, dec);
	}

	private Value parseExactValue(String string) throws ParseException {
		return exactValueFactory.create(numberFormat.parse(string)
				.doubleValue());
	}
}
