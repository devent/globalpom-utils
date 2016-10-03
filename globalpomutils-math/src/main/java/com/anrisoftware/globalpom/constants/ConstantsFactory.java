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
package com.anrisoftware.globalpom.constants;

import com.anrisoftware.globalpom.format.measurement.MeasureFormat;

/**
 * Factory to create the constants.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.10
 */
public interface ConstantsFactory {

	/**
	 * Creates the constants with the specified physical constants formatter.
	 * 
	 * @param format
	 *            the {@link MeasureFormat} physical constants formatter.
	 * 
	 * @return the {@link Constants}.
	 */
	Constants create(MeasureFormat format);
}
