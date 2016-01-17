/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
