/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
