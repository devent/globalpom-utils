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

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Factory to create an annotation filter from a collection of annotation types.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface AnnotationSetFilterFactory {

	/**
	 * Creates a new filter from the specified annotation type.
	 * 
	 * @param annotation
	 *            the annotation {@link Class} type.
	 * 
	 * @return the {@link AnnotationFilter}.
	 * 
	 * @since 1.5
	 */
	AnnotationFilter create(Class<? extends Annotation> annotation);

	/**
	 * Creates a new filter from the specified annotation types.
	 * 
	 * @param annotations
	 *            {@link Collection} that returns the annotation types.
	 * 
	 * @return the {@link AnnotationFilter}.
	 */
	AnnotationFilter create(
			Collection<? extends Class<? extends Annotation>> annotations);
}
