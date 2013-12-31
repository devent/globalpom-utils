/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpom-utils.
 *
 * globalpom-utils is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpom-utils is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpom-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.initfileparser;

import java.io.Serializable;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Mutable INI file attributes.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class DefaultInitFileAttributes implements InitFileAttributes,
        Serializable {

    private char[] sectionBrackets;

    private char comment;

    private char propertyDelimiter;

    private String defaultSectionName;

    /**
     * @see DefaultInitFileAttributesFactory#create()
     */
    @AssistedInject
    DefaultInitFileAttributes() {
        this.sectionBrackets = new char[] { '[', ']' };
        this.comment = '#';
        this.propertyDelimiter = '=';
        this.defaultSectionName = "Default";
    }

    /**
     * @see DefaultInitFileAttributesFactory#create(InitFileAttributes)
     */
    @AssistedInject
    DefaultInitFileAttributes(@Assisted InitFileAttributes attributes) {
        this.sectionBrackets = attributes.getSectionBrackets();
        this.comment = attributes.getComment();
        this.propertyDelimiter = attributes.getPropertyDelimiter();
        this.defaultSectionName = attributes.getDefaultSectionName();
    }

    public void setSectionBrackets(char[] brackets) {
        this.sectionBrackets = brackets;
    }

    @Override
    public char[] getSectionBrackets() {
        return sectionBrackets;
    }

    public void setComment(char comment) {
        this.comment = comment;
    }

    @Override
    public char getComment() {
        return comment;
    }

    public void setPropertyDelimiter(char delimiter) {
        this.propertyDelimiter = delimiter;
    }

    @Override
    public char getPropertyDelimiter() {
        return propertyDelimiter;
    }

    public void setDefaultSectionName(String name) {
        this.defaultSectionName = name;
    }

    @Override
    public String getDefaultSectionName() {
        return defaultSectionName;
    }
}
