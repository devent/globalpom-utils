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
package com.anrisoftware.globalpom.dataimport;

import static com.anrisoftware.globalpom.charset.SerializableCharset.decorate;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Locale;

import com.anrisoftware.globalpom.charset.SerializableCharset;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Mutable CSV import properties.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class DefaultCsvImportProperties implements CsvImportProperties,
		Serializable {

	private URI file;

	private SerializableCharset charset;

	private Locale locale;

	private char separator;

	private char quote;

	private String endOfLineSymbols;

	private int startRow;

	private int numCols;

	/**
	 * Sets system based default values.
	 */
	@AssistedInject
	public DefaultCsvImportProperties(@Assisted CsvImportProperties properties) {
		setCharset(properties.getCharset());
		setEndOfLineSymbols(properties.getEndOfLineSymbols());
		setFile(properties.getFile());
		setLocale(properties.getLocale());
		setNumCols(properties.getNumCols());
		setQuote(properties.getQuote());
		setSeparator(properties.getSeparator());
		setStartRow(properties.getStartRow());
	}

	/**
	 * Sets system based default values.
	 */
	@AssistedInject
	public DefaultCsvImportProperties() {
		this.file = new File("").toURI();
		this.charset = decorate(Charset.defaultCharset());
		this.locale = Locale.US;
		this.separator = ',';
		this.quote = '"';
		this.endOfLineSymbols = System.getProperty("line.separator");
		this.startRow = 0;
		this.numCols = 0;
	}

	public void setFile(URI file) {
		this.file = file;
	}

	@Override
	public URI getFile() {
		return file;
	}

	public void setCharset(Charset charset) {
		this.charset = decorate(charset);
	}

	@Override
	public Charset getCharset() {
		return charset.getCharset();
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	public void setSeparator(char separator) {
		this.separator = separator;
	}

	@Override
	public char getSeparator() {
		return separator;
	}

	public void setQuote(char quote) {
		this.quote = quote;
	}

	@Override
	public char getQuote() {
		return quote;
	}

	public void setEndOfLineSymbols(String symbols) {
		this.endOfLineSymbols = symbols;
	}

	@Override
	public String getEndOfLineSymbols() {
		return endOfLineSymbols;
	}

	public void setStartRow(int row) {
		this.startRow = row;
	}

	@Override
	public int getStartRow() {
		return startRow;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	@Override
	public int getNumCols() {
		return numCols;
	}
}
