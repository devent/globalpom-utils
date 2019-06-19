/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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
package com.anrisoftware.globalpom.core.version;

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

import static com.anrisoftware.globalpom.core.version.Version.MAJOR;
import static com.anrisoftware.globalpom.core.version.Version.MINOR;
import static com.anrisoftware.globalpom.core.version.Version.REV;

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
