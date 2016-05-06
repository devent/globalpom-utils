/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.colorformat;

import static com.google.inject.Guice.createInjector;
import static java.util.regex.Pattern.compile;

import java.awt.Color;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.regex.Pattern;

import javax.inject.Inject;

import com.google.inject.Injector;

/**
 * Parses a color.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.8
 */
@SuppressWarnings("serial")
public class ColorFormat extends Format {

	/**
	 * @see #create()
	 */
	public static ColorFormat createColorFormat() {
		return create();
	}

	/**
	 * Create the color format.
	 * 
	 * @return the {@link ColorFormat}.
	 */
	public static ColorFormat create() {
		return InjectorInstance.injector.getInstance(ColorFormat.class);
	}

	private static class InjectorInstance {
		static final Injector injector = createInjector();
	}

	private static final Pattern RGB = compile("#([0-9a-fA-F]{2}){3}");

	private static final Pattern ARGB = compile("#([0-9a-fA-F]{2}){4}");

	@Inject
	private ColorFormatLogger log;

	/**
	 * Formats the specified color to a hexadecimal color code.
	 * <p>
	 * <h2>Format</h2>
	 * <p>
	 * <ul>
	 * <li>{@code "#RGB"}
	 * <li>{@code "#ARGB"}
	 * </ul>
	 * 
	 * @param obj
	 *            the {@link Color}.
	 */
	@Override
	public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
		if (obj instanceof Color) {
			formatColor(buff, (Color) obj);
		}
		return buff;
	}

	private void formatColor(StringBuffer buff, Color color) {
		int a = color.getAlpha();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		if (a < 255) {
			buff.append(String.format("#%02x%02x%02x%02x", a, r, g, b));
		} else {
			buff.append(String.format("#%02x%02x%02x", r, g, b));
		}
	}

	/**
	 * Parses the specified string to a color.
	 * <p>
	 * <h2>Format</h2>
	 * <p>
	 * <ul>
	 * <li>{@code "#RGB"}
	 * <li>{@code "#ARGB"}
	 * </ul>
	 * 
	 * @see Color#decode(String)
	 */
	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return parse(source, pos);
	}

	/**
	 * @see #parse(String, ParsePosition)
	 */
	public Color parse(String source) throws ParseException {
		ParsePosition pos = new ParsePosition(0);
		Color result = parse(source, pos);
		if (pos.getIndex() == 0) {
			throw log.errorParseColor(source, pos);
		}
		return result;
	}

	/**
	 * Parses the specified string to a color.
	 * <p>
	 * <h2>Format</h2>
	 * <p>
	 * <ul>
	 * <li>{@code "#RGB"}
	 * <li>{@code "#ARGB"}
	 * </ul>
	 * 
	 * @param source
	 *            the source {@link String}.
	 * 
	 * @param pos
	 *            the index {@link ParsePosition} position from where to start
	 *            parsing.
	 * 
	 * @return the parsed {@link Color}.
	 * 
	 * @throws ParseException
	 *             if the string is not in the correct format.
	 */
	public Color parse(String source, ParsePosition pos) {
		try {
			source = source.substring(pos.getIndex());
			Color color = decodeColor(source);
			pos.setErrorIndex(-1);
			pos.setIndex(pos.getIndex() + source.length());
			return color;
		} catch (NumberFormatException e) {
			pos.setIndex(0);
			pos.setErrorIndex(0);
			return null;
		}
	}

	private Color decodeColor(String source) {
		if (RGB.matcher(source).matches()) {
			Integer val = Integer.decode(source);
			int i = val.intValue();
			return new Color(i, false);
		}
		if (ARGB.matcher(source).matches()) {
			Long val = Long.decode(source);
			int i = val.intValue();
			return new Color(i, true);
		}
		return Color.decode(source);
	}

}
