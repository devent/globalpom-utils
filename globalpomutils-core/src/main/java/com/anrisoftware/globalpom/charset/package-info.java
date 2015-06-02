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
 * Decorates a character set to enable serialization.
 * </p>
 *
 * <p>
 * Example with import static:
 * </p>
 *
 * <pre>
 * import static com.anrisoftware.globalpom.charset.SerializableCharset.decorateSerializableCharset;
 *
 * Charset charset = Charsets.UTF_8;
 * SerializableCharset serializable = decorateSerializableCharset(charset);
 * </pre>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.7
 */
package com.anrisoftware.globalpom.charset;
