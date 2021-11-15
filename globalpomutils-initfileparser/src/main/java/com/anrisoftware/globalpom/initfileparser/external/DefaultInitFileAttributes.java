/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
public class DefaultInitFileAttributes implements InitFileAttributes, Serializable {

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
        this.whitespaceBetweenPropertyDelimiter = a.isWhitespaceBetweenPropertyDelimiter();
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
     *
     * @param stringQuote the string quote character.
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
     *
     * @param stringQuoteEnabled if the string quote is enabled.
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
     *
     * @param multiValueMark the {@link String} multi value mark.
     */
    public void setMultiValueMark(String multiValueMark) {
        this.multiValueMark = multiValueMark;
    }

    @Override
    public String getMultiValueMark() {
        return multiValueMark;
    }

    /**
     *
     * @param name the {@link String} default section name.
     */
    public void setDefaultSectionName(String name) {
        this.defaultSectionName = name;
    }

    @Override
    public String getDefaultSectionName() {
        return defaultSectionName;
    }

    /**
     *
     * @param whitespace if whitespace between property delimiter is enabled.
     */
    public void setWhitespaceBetweenPropertyDelimiter(boolean whitespace) {
        this.whitespaceBetweenPropertyDelimiter = whitespace;
    }

    @Override
    public boolean isWhitespaceBetweenPropertyDelimiter() {
        return whitespaceBetweenPropertyDelimiter;
    }

    /**
     *
     * @param newLine the {@link String} new line character.
     */
    public void setNewLine(String newLine) {
        this.newLine = newLine;
    }

    @Override
    public String getNewLine() {
        return newLine;
    }

    /**
     *
     * @param allow if multi line properties are allowed.
     */
    public void setAllowMultiLineProperties(boolean allow) {
        this.allowMultiLineProperties = allow;
    }

    @Override
    public boolean isAllowMultiLineProperties() {
        return allowMultiLineProperties;
    }

}
