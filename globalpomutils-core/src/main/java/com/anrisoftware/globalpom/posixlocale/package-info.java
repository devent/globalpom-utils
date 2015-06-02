/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
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
 * import static com.anrisoftware.globalpom.posixlocale.PosixLocaleFormatModule.getPosixLocaleFormatFactory;
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
package com.anrisoftware.globalpom.posixlocale;

