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

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Binds the annotation access and annotation discovery.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 * 
 * @see AnnotationAccessFactory
 * @see AnnotationDiscoveryFactory
 * @see AnnotationSetFilterFactory
 */
public class AnnotationsModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(AnnotationAccess.class,
				AnnotationAccessImpl.class)
				.build(AnnotationAccessFactory.class));
		install(new FactoryModuleBuilder().implement(AnnotationDiscovery.class,
				AnnotationDiscoveryImpl.class).build(
				AnnotationDiscoveryFactory.class));
		install(new FactoryModuleBuilder().implement(AnnotationFilter.class,
				AnnotationSetFilter.class).build(
				AnnotationSetFilterFactory.class));
	}

}
