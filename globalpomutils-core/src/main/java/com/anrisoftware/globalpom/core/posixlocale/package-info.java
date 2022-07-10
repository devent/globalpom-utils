/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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
/**
 * <p>
 * Contains the POSIX locale class, that is a locale as used on POSIX
 * platforms. Contains the language, country and character set.
 * </p>
 *
 * <p>
 * The factory class <code>PosixLocaleFactory</code>
 * can be used to create a new POSIX locale object as described below.
 * </p>
 *
 * <pre>
 * public class Builder {
 *
 *     public void build() {
 *         Injector injector = Guice.createInjector(new PosixLocaleModule());
 *         Foo foo = injector.getInstance(Foo.class);
 *         PosixLocale locale = foo.getLocale();
 *     }
 * }
 *
 * public class Foo {
 *
 *     &#64;Inject
 *     private PosixLocaleFactory factory;
 *
 *     public PosixLocale getLocale() {
 *         return factory.create(locale, charset);
 *     }
 * }
 * </pre>
 *
 * <p>
 * The POSIX locale can be parsed and formatted via
 * the <code>PosixLocaleFormat</code> class.
 * The <code>PosixLocaleFormatModule</code> provides various factory methods
 * to create the POSIX locale format.
 * </p>
 *
 * <pre>
 * import static com.anrisoftware.globalpom.core.external.posixlocale.PosixLocaleFormatModule.getPosixLocaleFormatFactory;
 *
 * PosixLocaleFormat format = getPosixLocaleFormatFactory();
 * format.parse(string);
 * format.format(locale);
 * </pre>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.6
 * @see <a href="http://en.wikipedia.org/wiki/Locale">http://en.wikipedia.org/wiki/Locale</a>
 */
package com.anrisoftware.globalpom.core.posixlocale;

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

