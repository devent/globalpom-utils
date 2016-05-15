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
package com.anrisoftware.globalpom.external.initfileparser;

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

    private char stringQuote;

    private boolean stringQuoteEnabled;

    private String defaultSectionName;

    private boolean whitespaceBetweenPropertyDelimiter;

    private String newLine;

    private boolean allowMultiLineProperties;

    private String multiValueMark;

    /**
     * @see DefaultInitFileAttributesFactory#create()
     */
    @AssistedInject
    DefaultInitFileAttributes() {
        this.sectionBrackets = new char[] { '[', ']' };
        this.comment = '#';
        this.propertyDelimiter = '=';
        this.stringQuote = '"';
        this.stringQuoteEnabled = true;
        this.defaultSectionName = "Default";
        this.whitespaceBetweenPropertyDelimiter = true;
        this.newLine = System.getProperty("line.separator");
        this.multiValueMark = "[]";
        this.allowMultiLineProperties = true;
    }

    /**
     * @see DefaultInitFileAttributesFactory#create(InitFileAttributes)
     */
    @AssistedInject
    DefaultInitFileAttributes(@Assisted InitFileAttributes a) {
        this.sectionBrackets = a.getSectionBrackets();
        this.comment = a.getComment();
        this.propertyDelimiter = a.getPropertyDelimiter();
        this.stringQuote = a.getStringQuote();
        this.stringQuoteEnabled = a.isStringQuoteEnabled();
        this.defaultSectionName = a.getDefaultSectionName();
        this.whitespaceBetweenPropertyDelimiter = a
                .isWhitespaceBetweenPropertyDelimiter();
        this.multiValueMark = a.getMultiValueMark();
        this.newLine = a.getNewLine();
        this.allowMultiLineProperties = a.isAllowMultiLineProperties();
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

    /**
     * @since 2.3
     */
    public void setStringQuote(char stringQuote) {
        this.stringQuote = stringQuote;
    }

    @Override
    public char getStringQuote() {
        return stringQuote;
    }

    /**
     * @since 2.4
     */
    public void setStringQuoteEnabled(boolean stringQuoteEnabled) {
        this.stringQuoteEnabled = stringQuoteEnabled;
    }

    @Override
    public boolean isStringQuoteEnabled() {
        return stringQuoteEnabled;
    }

    /**
     * @since 2.3
     */
    public void setMultiValueMark(String multiValueMark) {
        this.multiValueMark = multiValueMark;
    }

    @Override
    public String getMultiValueMark() {
        return multiValueMark;
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
