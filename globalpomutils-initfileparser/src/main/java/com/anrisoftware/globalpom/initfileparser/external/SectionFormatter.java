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

/**
 * Formats the INI file section.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface SectionFormatter {

    /**
     * Formats the INI file section.
     * 
     * @param section
     *            the INI file {@link Section}.
     * 
     * @return the formatted {@link String}.
     */
    String format(Section section);

    /**
     * Formats the INI file section to the specified builder.
     * 
     * @param section
     *            the INI file {@link Section}.
     * 
     * @param builder
     *            the {@link StringBuilder}.
     * 
     * @return the appended {@link StringBuilder}.
     */
    StringBuilder format(Section section, StringBuilder builder);

    /**
     * Formats the INI file section to the specified buffer.
     * 
     * @param section
     *            the INI file {@link Section}.
     * 
     * @param builder
     *            the {@link StringBuffer}.
     * 
     * @return the appended {@link StringBuffer}.
     */
    StringBuffer format(Section section, StringBuffer builder);
}
