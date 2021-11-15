/*
 * Copyright 2014-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.reflection.annotations;

/*-
 * #%L
 * Global POM Utilities :: Reflection
 * %%
 * Copyright (C) 2014 - 2018 Advanced Natural Research Institute
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

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Search an object's fields and methods for annotations and if an annotation is
 * found it will inform the listeners.
 * 
 * @see AnnotationFilter
 * @see AnnotationListener
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationDiscovery extends
		Callable<Collection<AnnotationBean>> {

	/**
	 * Returns the filter that tests if the annotation have the correct type.
	 * 
	 * @return the {@link AnnotationFilter}.
	 */
	AnnotationFilter getFilter();

	/**
	 * Adds a new listener that is informed if a field or method is found with
	 * the expected annotation.
	 * 
	 * @param l
	 *            the {@link AnnotationListener}.
	 */
	void addListener(AnnotationListener l);

	/**
	 * Removed a listener that was informed if a field or method is found with
	 * the expected annotation.
	 * 
	 * @param l
	 *            the {@link AnnotationListener}.
	 */
	void removeListener(AnnotationListener l);

	/**
	 * Searched for annotations and returns the fields or methods that are
	 * annotated.
	 * 
	 * @return the {@link Collection} of the annotation bean. The collection
	 *         should not contain duplicates and the order of the fields and
	 *         methods should be in that they are found.
	 */
	@Override
	Collection<AnnotationBean> call();

}
