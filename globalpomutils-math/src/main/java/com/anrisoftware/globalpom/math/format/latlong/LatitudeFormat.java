/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.math.format.latlong;

import static com.anrisoftware.globalpom.math.format.latlong.LatLongFormatModule.getLatitudeFormatFactory;
import static javax.measure.unit.NonSI.DEGREE_ANGLE;
import static org.apache.commons.math3.util.FastMath.signum;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

import org.apache.commons.lang3.StringUtils;
import org.jscience.geography.coordinates.LatLong;

import com.anrisoftware.globalpom.math.format.degree.DegreeSexagesimalFormat;
import com.anrisoftware.globalpom.math.format.degree.DegreeSexagesimalFormatFactory;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import jakarta.inject.Inject;

/**
 * Parses the latitude part of a geographic coordinate.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
@SuppressWarnings("serial")
public class LatitudeFormat extends Format {

    private static final String NORTH = "N";

    private static final String SOUTH = "S";

    /**
     * @see #create(LatLong)
     * @param latLong the {@link LatLong} the latitude and longitude coordinates.
     * @return the latitude {@link LatitudeFormat} format.
     */
    public static LatitudeFormat createLatitudeFormat(LatLong latLong) {
        return create(latLong);
    }

    /**
     * @see #create(LatLong, int)
     * @param latLong the {@link LatLong} the latitude and longitude coordinates.
     * @param decimal the least significant decimal.
     * @return the latitude {@link LatitudeFormat} format.
     */
    public static LatitudeFormat createLatitudeFormat(LatLong latLong, int decimal) {
        return create(latLong, decimal);
    }

    /**
	 * @see #create(LatLong, int, NumberFormat)
	 * @param latLong the {@link LatLong} the latitude and longitude coordinates.
	 * @param decimal the least significant decimal.
	 * @param format  the {@link NumberFormat}.
	 * @return the latitude {@link LatitudeFormat} format.
	 */
	public static LatitudeFormat createLatitudeFormat(LatLong latLong, int decimal, NumberFormat format) {
		return create(latLong, decimal, format);
	}

	/**
	 * @see #create(LatLong, NumberFormat)
	 * @param latLong the {@link LatLong} the latitude and longitude coordinates.
	 * @param format  the {@link NumberFormat}.
	 * @return the latitude {@link LatitudeFormat} format.
	 */
	public static LatitudeFormat createLatitudeFormat(LatLong latLong, NumberFormat format) {
		return create(latLong, 3, format);
	}

	/**
	 * Creates the latitude format.
	 *
	 * @param latLong the {@link LatLong} the latitude and longitude coordinates.
	 * @return the latitude {@link LatitudeFormat} format.
	 */
    public static LatitudeFormat create(LatLong latLong) {
        return getLatitudeFormatFactory().create(latLong);
    }

    /**
     * Creates the latitude format.
     *
     * @param latLong the {@link LatLong} the latitude and longitude coordinates.
     * @param decimal the least significant decimal.
     * @return the latitude {@link LatitudeFormat} format.
     */
    public static LatitudeFormat create(LatLong latLong, int decimal) {
        return getLatitudeFormatFactory().create(latLong, decimal);
    }

	/**
	 * Creates the latitude format.
	 *
	 * @param latLong the {@link LatLong} the latitude and longitude coordinates.
	 * @param decimal the least significant decimal.
	 * @param format  the {@link NumberFormat}.
	 * @return the latitude {@link LatitudeFormat} format.
	 */
	public static LatitudeFormat create(LatLong latLong, int decimal, NumberFormat format) {
		return getLatitudeFormatFactory().create(latLong, decimal, format);
	}

    private final int decimal;

    private final LatLong latLong;

    @Inject
    private LatitudeFormatLogger log;

    @Inject
    private DegreeSexagesimalFormatFactory sexagesimalFormatFactory;

	private final NumberFormat format;

    /**
     * @see LatitudeFormatFactory#create(LatLong)
     */
    @AssistedInject
    LatitudeFormat(@Assisted LatLong latLong) {
        this(latLong, 3);
    }

    /**
     * @see LatitudeFormatFactory#create(LatLong, int)
     */
    @AssistedInject
    LatitudeFormat(@Assisted LatLong latLong, @Assisted int decimal) {
		this(latLong, decimal, NumberFormat.getInstance());
    }

    /**
	 * @see LatitudeFormatFactory#create(LatLong, int, NumberFormat)
	 */
	@AssistedInject
	LatitudeFormat(@Assisted LatLong latLong, @Assisted int decimal, @Assisted NumberFormat format) {
		this.latLong = latLong;
		this.decimal = decimal;
		this.format = format;
	}

	/**
	 * Formats the latitude part of the specified latitude/longitude coordinates.
	 *
	 * @param obj the {@link LatLong}.
	 */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof LatLong) {
            formatCoordinate(buff, ((LatLong) obj).latitudeValue(DEGREE_ANGLE));
        }
        return buff;
    }

    private void formatCoordinate(StringBuffer buff, double d) {
		String s = sexagesimalFormatFactory.create(decimal, format).format(d);
        if (StringUtils.startsWith(s, "-")) {
            s = s.substring(1);
        }
        buff.append(s);
        if (signum(d) > 0.0) {
            buff.append(" ");
            buff.append(NORTH);
        } else {
            buff.append(" ");
            buff.append(SOUTH);
        }
    }

    /**
     * Parses the latitude part to a latitude/longitude coordinate.
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * Parses the latitude part to a latitude/longitude coordinate.
     *
     * @param source the {@link String} source.
     *
     * @return the parsed {@link LatLong}.
     *
     * @throws ParseException if the string can not be parsed.
     */
    public LatLong parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        LatLong result = parse(source, pos);
        if (pos.getIndex() == 0) {
            throw log.errorParseLatitude(source, pos);
        }
        return result;
    }

    /**
     * Parses the the latitude part with the specified index to a latitude/longitude
     * coordinate.
     *
     * @param source the source string.
     *
     * @param pos    the index {@link ParsePosition} position from where to start
     *               parsing.
     *
     * @return the parsed {@link LatLong}.
     */
    public LatLong parse(String source, ParsePosition pos) {
        try {
            source = source.substring(pos.getIndex());
            LatLong latlong = parse(this.latLong, source, pos);
            pos.setErrorIndex(-1);
            pos.setIndex(source.length());
            return latlong;
        } catch (ParseException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private LatLong parse(LatLong latLong, String string, ParsePosition pos) throws ParseException {
        double longi = latLong.longitudeValue(DEGREE_ANGLE);
        if (DegreeSexagesimalFormat.isValidFormat(string)) {
            log.checkLatitude(string, pos);
            double lat = parseSexagesimal(string);
            return LatLong.valueOf(lat, longi, DEGREE_ANGLE);
        } else {
            double lat = parseDecimal(string);
            return LatLong.valueOf(lat, longi, DEGREE_ANGLE);
        }
    }

    private double parseDecimal(String latitude) throws ParseException {
		return ((Number) format.parseObject(latitude)).doubleValue();
    }

    private double parseSexagesimal(String latitude) throws ParseException {
		return sexagesimalFormatFactory.create(decimal, format).parse(latitude).doubleValue(DEGREE_ANGLE);
    }

}
