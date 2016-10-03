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
package com.anrisoftware.globalpom.pointformat;

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
