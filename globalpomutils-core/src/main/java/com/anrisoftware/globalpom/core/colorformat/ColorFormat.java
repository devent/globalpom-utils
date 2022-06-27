/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.colorformat;

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
     *
     * @return the {@link ColorFormat}.
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
     * <h2>Format</h2>
     * <ul>
     * <li>{@code "#RGB"}
     * <li>{@code "#ARGB"}
     * </ul>
     *
     * @param obj the {@link Color}.
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
     * <h2>Format</h2>
     * <ul>
     * <li>{@code "#RGB"}
     * <li>{@code "#ARGB"}
     * </ul>
     *
     * @see Color#decode(String)
     * @return the parsed {@link Color}.
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * @param source the source {@link String}.
     *
     * @see #parse(String, ParsePosition)
     *
     * @return the parsed {@link Color}.
     *
     * @throws ParseException if the string is not in the correct format.
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
     * <h2>Format</h2>
     * <ul>
     * <li>{@code "#RGB"}
     * <li>{@code "#ARGB"}
     * </ul>
     *
     * @param source the source {@link String}.
     *
     * @param pos    the index {@link ParsePosition} position from where to start
     *               parsing.
     *
     * @return the parsed {@link Color} or {@code null}.
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
