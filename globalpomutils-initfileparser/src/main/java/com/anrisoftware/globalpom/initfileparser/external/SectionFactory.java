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
package com.anrisoftware.globalpom.initfileparser.external;

/*-
 * #%L
 * Global POM Utilities :: Init File Parser
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

import java.util.Properties;

/**
 * Factory to create the INI file section.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface SectionFactory {

    /**
     * Creates the INI file section.
     * 
     * @param name
     *            the {@link String} name.
     * 
     * @param properties
     *            the {@link Properties} properties.
     * 
     * @return the {@link Section}.
     */
    Section create(String name, Properties properties);
}
