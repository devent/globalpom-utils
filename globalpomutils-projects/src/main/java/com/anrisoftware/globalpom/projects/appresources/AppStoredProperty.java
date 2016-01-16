/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-projects.
 *
 * globalpomutils-projects is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-projects is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-projects. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.projects.appresources;

import java.io.File;
import java.net.URI;
import java.util.Deque;
import java.util.Locale;

/**
 * Stored application property.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public enum AppStoredProperty {

    /**
     * The {@link Deque} with the recently saved and loaded project {@link URI}
     * URIs property.
     */
    RECENT_PROJECTS_PROPERTY,

    /**
     * Last {@link File} directory from where the user have saved or loaded
     * resources.
     */
    LAST_DIRECTORY_PROPERTY,

    /**
     * Last export directory property.
     */
    LAST_EXPORT_DIRECTORY_PROPERTY,

    /**
     * The {@link Locale} locale property.
     */
    LOCALE_PROPERTY,

    /**
     * Load the last project on application start.
     */
    LOAD_LAST_PROJECT_PROPERTY,

    /**
     * The last project {@link URI}.
     */
    LAST_PROJECT_PROPERTY,

    /**
     * The last imported {@link File} file or {@code null}.
     */
    LAST_IMPORTED_FILE_PROPERTY,

}
