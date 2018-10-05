/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.core.pointformat;

/*-
 * #%L
 * Global POM Utilities :: Core
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

import static org.apache.commons.lang3.StringUtils.remove;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Formats and parses a {@link Point2D} point.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
@SuppressWarnings("serial")
public class PointFormat extends Format {

	private final NumberFormat numberFormat;

	private final PointFormatLogger log;

	/**
	 * Uses the default decimal format to format the X and Y coordinates of a
	 * point.
	 */
	public PointFormat() {
		this(new PointFormatLogger(), NumberFormat.getInstance());
	}

	/**
	 * Uses the default decimal format to format the X and Y coordinates of a
	 * point.
	 * 
	 * @param logger
	 *            the {@link PointFormatLogger} for logging messages.
	 */
	@AssistedInject
	PointFormat(PointFormatLogger logger) {
		this(logger, NumberFormat.getInstance());
	}

	/**
	 * Sets the number format to format the X and Y coordinates of a point.
	 * 
	 * @param logger
	 *            the {@link PointFormatLogger} for logging messages.
	 * 
	 * @param format
	 *            the {@link NumberFormat}.
	 */
	@AssistedInject
	PointFormat(PointFormatLogger logger, @Assisted NumberFormat format) {
		this.log = logger;
		this.numberFormat = format;
	}

	/**
	 * Formats the specified point.
	 * <p>
	 * The format follows the pattern: {@code (x, y)}.
	 * 
	 * @param obj
	 *            the {@link Point2D}.
	 */
	@Override
	public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
		if (obj instanceof Point2D) {
			Point2D point = (Point2D) obj;
			formatPoint(buff, point);
		}
		return buff;
	}

	private void formatPoint(StringBuffer buff, Point2D point) {
		buff.append("(");
		numberFormat.format(point.getX(), buff, new FieldPosition(0));
		buff.append(", ");
		numberFormat.format(point.getY(), buff, new FieldPosition(0));
		buff.append(")");
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return parse(source, pos);
	}

	/**
	 * Parses the specified string to a point.
	 * <p>
	 * The parser expects the patterns:
	 * 
	 * <ul>
	 * <li><code>(x, y)</code></li>
	 * <li><code>(x,y)</code></li>
	 * <li><code>x, y</code></li>
	 * <li><code>x,y</code></li>
	 * </ul>
	 * 
	 * @return the parsed {@link Point2D}.
	 * 
	 * @throws ParseException
	 *             if the string cannot be parsed to a point.
	 */
	public Point2D parse(String source) throws ParseException {
		return parse(source, new Point2D.Double());
	}

	/**
	 * Parses the specified string to a point.
	 * <p>
	 * The parser expects the patterns:
	 * 
	 * <ul>
	 * <li><code>(x, y)</code></li>
	 * <li><code>(x,y)</code></li>
	 * <li><code>x, y</code></li>
	 * <li><code>x,y</code></li>
	 * </ul>
	 * 
	 * @param point
	 *            the {@link Point2D} that should be parsed. If {@code null} a
	 *            {@link Point2D#Double} is used.
	 * 
	 * @return the parsed {@link Point2D}.
	 * 
	 * @throws ParseException
	 *             if the string cannot be parsed to a point.
	 * 
	 * @see Point
	 * @see Point2D#Double
	 * @see Point2D#Float
	 */
	public Point2D parse(String source, Point2D point) throws ParseException {
		ParsePosition pos = new ParsePosition(0);
		Point2D result = parse(source, pos, point);
		if (pos.getIndex() == 0) {
			throw log.errorParsePoint(source, pos);
		}
		return result;
	}

	/**
	 * Parses the specified string to a point.
	 * <p>
	 * The parser expects the patterns:
	 * 
	 * <ul>
	 * <li>{@code (x, y)}</li>
	 * <li>{@code (x,y)}</li>
	 * <li>{@code x, y}</li>
	 * <li>{@code x,y}</li>
	 * </ul>
	 * 
	 * @param source
	 *            the source string.
	 * 
	 * @param pos
	 *            the index {@link ParsePosition} position from where to start
	 *            parsing.
	 * 
	 * @return the parsed {@link Point2D}.
	 */
	public Point2D parse(String source, ParsePosition pos) {
		return parse(source, pos, new Point2D.Double());
	}

	/**
	 * Parses the specified string to a point.
	 * <p>
	 * The parser expects the patterns:
	 * 
	 * <ul>
	 * <li>{@code (x, y)}</li>
	 * <li>{@code (x,y)}</li>
	 * <li>{@code x, y}</li>
	 * <li>{@code x,y}</li>
	 * </ul>
	 * 
	 * @param source
	 *            the source string.
	 * 
	 * @param pos
	 *            the index {@link ParsePosition} position from where to start
	 *            parsing.
	 * 
	 * @param point
	 *            the {@link Point2D} that should be parsed. If {@code null} a
	 *            {@link Point2D#Double} is used.
	 * 
	 * @return the parsed {@link Point2D}.
	 * 
	 * @see Point
	 * @see Point2D#Double
	 * @see Point2D#Float
	 */
	public Point2D parse(String source, ParsePosition pos, Point2D point) {
		source = source.substring(pos.getIndex());
		try {
			Point2D address = parsePoint(source, point);
			pos.setErrorIndex(-1);
			pos.setIndex(source.length());
			return address;
		} catch (ParseException e) {
			pos.setIndex(0);
			pos.setErrorIndex(0);
			return null;
		}
	}

	private Point2D parsePoint(String string, Point2D point)
			throws ParseException {
		if (point == null) {
			point = new Point2D.Double();
		}
		string = remove(string, "(");
		string = remove(string, ")");
		String[] split = StringUtils.split(string, ",");
		if (split.length != 2) {
			throw log.errorTwoPointsNeeded(string);
		}
		double x = numberFormat.parse(split[0].trim()).doubleValue();
		double y = numberFormat.parse(split[1].trim()).doubleValue();
		point.setLocation(x, y);
		return point;
	}
}
