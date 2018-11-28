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

package com.anrisoftware.globalpom.reflection.beans;

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

import java.lang.reflect.Field;

import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Factory to create a new access to a Java bean. If both the getter method and
 * the field are defined then any information and access will be through the
 * getter method first.
 * 
 * @see BeanAccess
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface BeanAccessFactory {

	/**
	 * Creates the bean access to the specified field.
	 * 
	 * @param name
	 *            the field name. The access will be either through the
	 *            getter/setter methods or through the field directly if no
	 *            getter/setter methods are defined. Must have a getter method
	 *            or a field in the bean to identify the type.
	 * 
	 * @param bean
	 *            the bean {@link Object}.
	 * 
	 * @return the {@link BeanAccess}.
	 * 
	 * @throws NullPointerException
	 *             if the name or the parent object is {@code null}.
	 * 
	 * @throws ReflectionError
	 *             if no field or field getter are defined for the field.
	 */
	BeanAccess create(String name, Object bean);

	/**
	 * Creates the bean access to the specified field.
	 * 
	 * @param field
	 *            the {@link Field}. The access will be either through the
	 *            getter/setter methods of the field or through the field
	 *            directly if no getter/setter methods are defined.
	 * 
	 * @param bean
	 *            the bean {@link Object}.
	 * 
	 * @return the {@link BeanAccess}.
	 * 
	 * @throws NullPointerException
	 *             if the field or the parent object is {@code null}.
	 */
	BeanAccess create(Field field, Object bean);
}
