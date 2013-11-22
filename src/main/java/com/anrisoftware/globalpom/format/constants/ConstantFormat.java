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
package com.anrisoftware.globalpom.format.constants;

import static org.apache.commons.lang3.StringUtils.remove;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.measure.unit.Unit;
import javax.measure.unit.UnitFormat;

import com.anrisoftware.globalpom.constants.Constant;
import com.anrisoftware.globalpom.constants.ConstantFactory;
import com.anrisoftware.globalpom.constants.ValueToString;
import com.anrisoftware.globalpom.measurement.Value;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Formats and parses physical {@link Constant} constant.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class ConstantFormat extends Format {

	private final Pattern VALUE_PATTERN = Pattern.compile("^(.*?;)+((.*?);)$");

	private final Format valueFormat;

	private final NumberFormat numberFormat;

	private final ConstantFactory constantFactory;

	@Inject
	private ValueToString valueToString;

	@Inject
	private ConstantFormatLogger log;

	/**
	 * @see ConstantFormatFactory#create(ConstantFactory, Format)
	 */
	@AssistedInject
	ConstantFormat(@Assisted ConstantFactory constantFactory,
			@Assisted Format valueFormat) {
		this(constantFactory, valueFormat, new DecimalFormat("#.#########"));
	}

	/**
	 * @see ConstantFormatFactory#create(ConstantFactory, Format, NumberFormat)
	 */
	@AssistedInject
	ConstantFormat(@Assisted ConstantFactory constantFactory,
			@Assisted Format valueFormat, @Assisted NumberFormat format) {
		this.constantFactory = constantFactory;
		this.valueFormat = valueFormat;
		this.numberFormat = format;
	}

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
	 * @param obj
	 *            the {@link Constant}.
	 */
	@Override
	public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
		if (obj instanceof Constant) {
			valueToString.format(buff, (Constant<?>) obj, numberFormat);
		}
		return buff;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return parse(source, pos);
	}

	/**
	 * Parses the specified string to physical constant.
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
			Value address = parseValue(source, pos);
			pos.setErrorIndex(-1);
			pos.setIndex(source.length());
			return address;
		} catch (ParseException e) {
			pos.setIndex(0);
			pos.setErrorIndex(0);
			return null;
		}
	}

	private Value parseValue(String string, ParsePosition pos)
			throws ParseException {
		Matcher matcher = VALUE_PATTERN.matcher(string);
		log.checkString(matcher, string, pos);
		String valuestr = matcher.group(1);
		String unitstr = matcher.group(2);
		valuestr = remove(string, unitstr);
		Value value = (Value) valueFormat.parseObject(valuestr);
		unitstr = matcher.group(3);
		Unit<?> unit = (Unit<?>) UnitFormat.getInstance().parseObject(unitstr);
		return constantFactory.create(value, unit);
	}
}
