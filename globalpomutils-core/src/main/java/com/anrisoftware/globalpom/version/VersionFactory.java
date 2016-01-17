/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.version;

import static com.anrisoftware.globalpom.version.Version.MAJOR;
import static com.anrisoftware.globalpom.version.Version.MINOR;
import static com.anrisoftware.globalpom.version.Version.REV;

import com.google.inject.assistedinject.Assisted;

/**
 * Factory to create the version.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
public interface VersionFactory {

    /**
     * Creates the version from the specified version numbers.
     *
     * @param major
     *            the major version number.
     *
     * @param minor
     *            the minor version number.
     *
     * @param rev
     *            the revision version number.
     *
     * @return the {@link Version}.
     */
    Version create(@Assisted(MAJOR) int major, @Assisted(MINOR) int minor,
            @Assisted(REV) int rev);
}
