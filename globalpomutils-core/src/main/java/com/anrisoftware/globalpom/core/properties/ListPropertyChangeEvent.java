/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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
package com.anrisoftware.globalpom.core.properties;

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

import java.beans.PropertyChangeEvent;

/**
 * Event when a list property have been changed.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public abstract class ListPropertyChangeEvent extends PropertyChangeEvent {

	private final int index0;

	private final int index1;

	/**
	 * @see PropertyChangeEvent#PropertyChangeEvent(Object, String, Object,
	 *      Object)
	 *
	 * @param index0
	 *            the first index where the list was changed.
	 *
	 * @param index1
	 *            the last index where the list was changed.
	 */
	public ListPropertyChangeEvent(Object source, String propertyName,
			int index0, int index1, Object oldValue, Object newValue) {
		super(source, propertyName, oldValue, newValue);
		this.index0 = index0;
		this.index1 = index1;
	}

	/**
	 * Returns the first index where the list was changed.
	 *
	 * @return the first index.
	 */
	public int getIndex0() {
		return index0;
	}

	/**
	 * Returns the last index where the list was changed.
	 *
	 * @return the last index.
	 */
	public int getIndex1() {
		return index1;
	}

	/**
	 * Returns the type of list change event.
	 *
	 * @return the {@link ListPropertyChange}.
	 */
	public abstract ListPropertyChange getType();
}
