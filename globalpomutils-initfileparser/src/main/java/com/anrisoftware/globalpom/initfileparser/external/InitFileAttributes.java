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
 * INI file attributes.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface InitFileAttributes {

    /**
     * Returns the opening and closing section brackets, for example
     * {@code '[', ']'.}
     *
     * @return first index returns the opening and second index returns the
     *         closing bracket.
     */
    char[] getSectionBrackets();

    /**
     * Returns the line comment character, for example {@code ';'} or
     * {@code '#'.}
     *
     * @return the line comment character.
     */
    char getComment();

    /**
     * Returns the property delimiter character, for example {@code '='.}
     *
     * @return the property delimiter character.
     */
    char getPropertyDelimiter();

    /**
     * Returns the string quotation character, for example {@code '"'.}
     *
     * @return the string quotation character.
     *
     * @since 2.3
     */
    char getStringQuote();

    /**
     * Returns if the string quotation is enabled.
     *
     * @return {@code true} if the string quotation is enabled.
     *
     * @since 2.4
     */
    boolean isStringQuoteEnabled();

    /**
     * Returns the marker of multi-valued properties, for example {@code "[]".}
     *
     * @return the multi-value {@link String} mark.
     *
     * @since 2.3
     */
    String getMultiValueMark();

    /**
     * Returns the new line characters, for example {@code "\n".}
     *
     * @return the new line {@link String} characters.
     */
    String getNewLine();

    /**
     * Returns to add whitespace between the property delimiter so the property
     * is like {@code "key = value".}
     *
     * @return {@code true} to add whitespace.
     */
    boolean isWhitespaceBetweenPropertyDelimiter();

    /**
     * Returns the name of the default section. The section is used when a
     * property is found with no previous section declaration.
     *
     * @return the {@link String} name of the default section.
     */
    String getDefaultSectionName();

    /**
     * Returns to allow multi-line properties.
     *
     * @return {@code true} to allow multi-line properties.
     */
    boolean isAllowMultiLineProperties();

}
