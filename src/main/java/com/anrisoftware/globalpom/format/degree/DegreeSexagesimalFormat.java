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
package com.anrisoftware.globalpom.format.degree;

import static com.anrisoftware.globalpom.measurement.RoundToSignificantFigures.roundToDecimal;
import static com.google.inject.Guice.createInjector;
import static java.lang.Double.parseDouble;
import static java.util.regex.Pattern.compile;
import static javax.measure.unit.NonSI.DEGREE_ANGLE;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.measure.quantity.Angle;

import org.apache.commons.math3.util.FastMath;
import org.jscience.physics.amount.Amount;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Parses angle degree sexagesimal.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class DegreeSexagesimalFormat extends Format {

	private static final String SEC_SUB = "\"";

	private static final String MIN_SUB = "'";

	private static final String DEGREE_SUB = "°";

	/**
	 * @see #create()
	 */
	public static DegreeSexagesimalFormat createDegreeSexagesimalFormat() {
		return create();
	}

	/**
	 * @see #create(int)
	 */
	public static DegreeSexagesimalFormat create() {
		return InjectorInstance.factory.create(4);
	}

	/**
	 * Create the sexagesimal degree format.
	 * 
	 * @return the {@link DegreeSexagesimalFormat}.
	 */
	public static DegreeSexagesimalFormat create(int decimal) {
		return InjectorInstance.factory.create(decimal);
	}

	private static class InjectorInstance {
		static final DegreeSexagesimalFormatFactory factory = createInjector(
				new DegreeSexagesimalFormatModule()).getInstance(
				DegreeSexagesimalFormatFactory.class);
	}

	private static final Pattern PATTERN = compile(String.format(
			"^((\\d+)%s)((\\d+)%s)?((\\d+(\\.\\d*)?)%s)?$", DEGREE_SUB,
			MIN_SUB, SEC_SUB));

	private static final double MIN = 1d / 60d;

	private static final double SEC = 1d / 3600d;

	@Inject
	private DegreeSexagesimalFormatLogger log;

	private final int decimal;

	private final double epsilon;

	@AssistedInject
	DegreeSexagesimalFormat() {
		this(3);
	}

	@AssistedInject
	DegreeSexagesimalFormat(@Assisted int decimal) {
		this.decimal = decimal;
		this.epsilon = FastMath.pow(10, -(decimal + 1));
	}

	/**
	 * Formats the specified degree sexagesimal.
	 * 
	 * @param obj
	 *            the {@link Amount}.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
		if (obj instanceof Amount) {
			formatFormat(buff, (Amount<Angle>) obj);
		}
		return buff;
	}

	private void formatFormat(StringBuffer buff, Amount<Angle> obj) {
		NumberFormat format = DecimalFormat.getNumberInstance();
		double value = obj.doubleValue(DEGREE_ANGLE);
		double degree = (long) value;
		double dmin = (value - degree) / MIN;
		double min = roundToDecimal((long) dmin, decimal);
		double sec = roundToDecimal((dmin - min) / MIN, decimal);
		buff.append(format.format(degree));
		buff.append(DEGREE_SUB);
		if (min > epsilon) {
			buff.append(format.format(min));
			buff.append(MIN_SUB);
		}
		if (sec > epsilon) {
			buff.append(format.format(sec));
			buff.append(SEC_SUB);
		}
	}

	/**
	 * Parses the specified string to degree sexagesimal.
	 * <p>
	 * <h2>Format</h2>
	 * <p>
	 * <ul>
	 * <li>{@code "D°M'S.s""}
	 * </ul>
	 * 
	 * @return the parsed {@link Amount}.
	 */
	@Override
	public Object parseObject(String source, ParsePosition pos) {
		try {
			return parse(source, pos);
		} catch (ParseException e) {
			pos.setErrorIndex(pos.getIndex() + e.getErrorOffset());
			return null;
		}
	}

	/**
	 * @see #parse(String, ParsePosition)
	 */
	public Amount<Angle> parse(String source) throws ParseException {
		ParsePosition pos = new ParsePosition(0);
		Amount<Angle> result = parse(source, pos);
		if (pos.getIndex() == 0) {
			throw log.errorParse(source, pos);
		}
		return result;
	}

	/**
	 * @see #parseObject(String)
	 * 
	 * @param pos
	 *            the index {@link ParsePosition} position from where to start
	 *            parsing.
	 * 
	 * @throws ParseException
	 *             if the string is not in the correct format.
	 */
	public Amount<Angle> parse(String source, ParsePosition pos)
			throws ParseException {
		try {
			source = source.substring(pos.getIndex());
			Amount<Angle> result = decodeAngle(source, pos);
			pos.setErrorIndex(-1);
			pos.setIndex(pos.getIndex() + source.length());
			return result;
		} catch (NumberFormatException e) {
			log.errorParseNumber(e, source);
			pos.setIndex(0);
			pos.setErrorIndex(0);
			return null;
		}
	}

	private Amount<Angle> decodeAngle(String source, ParsePosition pos)
			throws ParseException {
		Matcher matcher = PATTERN.matcher(source);
		log.checkMatches(matcher.find(), source, pos);
		double degree = parseDoubleSave(matcher.group(2));
		double min = parseDoubleSave(matcher.group(4)) * MIN;
		double sec = parseDoubleSave(matcher.group(6)) * SEC;
		return Amount.valueOf(roundToDecimal(degree + min + sec, decimal), 0,
				DEGREE_ANGLE);
	}

	private double parseDoubleSave(String string) {
		return string == null ? 0 : string.isEmpty() ? 0 : parseDouble(string);
	}
}
