/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-threads.
 *
 * globalpomutils-threads is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-threads is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-threads. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.threads.properties.internal;

import com.anrisoftware.propertiesutils.ContextProperties;

/**
 * Factory to create the threading properties from a properties file.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 3.1
 */
interface DefaultThreadingPropertiesFactory {

    /**
     * Creates the threading properties.
     *
     * @param properties
     *            the {@link ContextProperties} properties that were loaded.
     *
     * @param name
     *            the {@link String} name of the thread pool.
     *
     * @return the {@link ThreadingProperties}.
     */
    ThreadingProperties create(ContextProperties properties, String name);
}
