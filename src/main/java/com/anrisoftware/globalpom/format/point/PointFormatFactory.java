package com.anrisoftware.globalpom.format.point;

import java.text.NumberFormat;

/**
 * Factory to create a new point format.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.1
 */
public interface PointFormatFactory {

	/**
	 * Create point format that uses the default decimal format to format the X
	 * and Y coordinates of a point.
	 * 
	 * @return the {@link PointFormat}.
	 */
	PointFormat defaultFormat();

	/**
	 * Create point format with the specified decimal format to format the X and
	 * Y coordinates of a point.
	 * 
	 * @param format
	 *            the {@link NumberFormat}.
	 * 
	 * @return the {@link PointFormat}.
	 */
	PointFormat create(NumberFormat format);
}
