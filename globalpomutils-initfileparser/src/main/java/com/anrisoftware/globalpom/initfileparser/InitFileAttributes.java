/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-initfileparser.
 *
 * globalpomutils-initfileparser is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-initfileparser is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-initfileparser. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.initfileparser;

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
