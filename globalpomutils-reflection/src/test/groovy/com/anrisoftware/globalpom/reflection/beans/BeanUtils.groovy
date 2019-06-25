/**
 * Copyright © 2014 Erwin Müller (erwin.mueller@anrisoftware.com)
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
package com.anrisoftware.globalpom.reflection.beans

import com.anrisoftware.globalpom.utils.TestUtils
import com.google.inject.Guice
import com.google.inject.Injector

/**
 * Create the injector to test bean access.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanUtils extends TestUtils {

	static Injector createInjector() {
		Guice.createInjector new BeansModule()
	}

	static BeanAccessFactory createFactory(Injector injector) {
		injector.getInstance BeanAccessFactory
	}
}
