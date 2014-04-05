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

    private boolean whitespaceBetweenPropertyDelimiter;

    private String newLine;

    private boolean allowMultiLineProperties;

    /**
     * @see DefaultInitFileAttributesFactory#create()
     */
    @AssistedInject
    DefaultInitFileAttributes() {
        this.sectionBrackets = new char[] { '[', ']' };
        this.comment = '#';
        this.propertyDelimiter = '=';
        this.defaultSectionName = "Default";
        this.whitespaceBetweenPropertyDelimiter = true;
        this.newLine = System.getProperty("line.separator");
        this.allowMultiLineProperties = true;
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
        this.whitespaceBetweenPropertyDelimiter = attributes
                .isWhitespaceBetweenPropertyDelimiter();
        this.newLine = attributes.getNewLine();
        this.allowMultiLineProperties = attributes.isAllowMultiLineProperties();
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

    public void setWhitespaceBetweenPropertyDelimiter(boolean whitespace) {
        this.whitespaceBetweenPropertyDelimiter = whitespace;
    }

    @Override
    public boolean isWhitespaceBetweenPropertyDelimiter() {
        return whitespaceBetweenPropertyDelimiter;
    }

    public void setNewLine(String newLine) {
        this.newLine = newLine;
    }

    @Override
    public String getNewLine() {
        return newLine;
    }

    public void setAllowMultiLineProperties(boolean allow) {
        this.allowMultiLineProperties = allow;
    }

    @Override
    public boolean isAllowMultiLineProperties() {
        return allowMultiLineProperties;
    }

}
