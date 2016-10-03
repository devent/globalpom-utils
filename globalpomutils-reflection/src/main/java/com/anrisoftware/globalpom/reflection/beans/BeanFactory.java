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
package com.anrisoftware.globalpom.reflection.beans;

import com.anrisoftware.globalpom.reflection.exceptions.ReflectionError;

/**
 * Creates bean objects.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
public interface BeanFactory {

	/**
	 * Creates the bean with the standard constructor.
	 * 
	 * @param type
	 *            the {@link Class} type of the bean. The bean must have the
	 *            standard constructor available for initialization.
	 * 
	 * @return the bean.
	 * 
	 * @throws ReflectionError
	 *             if the standard constructor can not be found or can not be
	 *             accessed, if the constructor throws an exception or if the
	 *             type can not be instantiated.
	 */
	<T> T create(Class<T> type);

	/**
	 * Creates the bean with the standard constructor.
	 * 
	 * @param typeName
	 *            the name of the type of the bean. The bean must have the
	 *            standard constructor available for initialization.
	 * 
	 * @return the bean.
	 * 
	 * @throws ReflectionError
	 *             if the type name can not be found, if the standard
	 *             constructor can not be found or can not be accessed, if the
	 *             constructor throws an exception or if the type can not be
	 *             instantiated.
	 */
	<T> T create(String typeName);
}
