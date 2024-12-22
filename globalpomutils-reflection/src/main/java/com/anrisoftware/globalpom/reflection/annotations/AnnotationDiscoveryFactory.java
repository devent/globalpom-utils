/*
 * Copyright 2014-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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

/**
 * Factory to set the filter and the object for which annotations should be
 * found.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationDiscoveryFactory {

	/**
	 * Creates the new annotation search.
	 * 
	 * @param bean
	 *            the {@link Object}.
	 * 
	 * @param filter
	 *            the {@link AnnotationFilter} that will be used to filter the
	 *            annotation types.
	 * 
	 * @return the new {@link AnnotationDiscovery}.
	 */
	AnnotationDiscovery create(Object bean, AnnotationFilter filter);
}
