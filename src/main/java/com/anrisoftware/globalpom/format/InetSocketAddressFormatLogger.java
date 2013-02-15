/*
 * Copyright 2012-2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of fdsanalysis-keysgui-mainwindow. All rights reserved.
 */
package com.anrisoftware.globalpom.format;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.ParsePosition;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link InetSocketAddressFormat}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class InetSocketAddressFormatLogger extends AbstractLogger {

	/**
	 * Create logger for {@link InetSocketAddressFormat}.
	 */
	InetSocketAddressFormatLogger() {
		super(InetSocketAddressFormat.class);
	}

	ParseException errorParseAddress(String source, ParsePosition pos) {
		ParseException ex = new ParseException(String.format(
				"Unparseable socket address: '%s'", source),
				pos.getErrorIndex());
		log.error(ex.getLocalizedMessage());
		return ex;
	}

	URISyntaxException errorURISyntax(URI uri) {
		URISyntaxException ex = new URISyntaxException(uri.toString(),
				"URI must have a host part");
		log.error(ex.getLocalizedMessage());
		return ex;
	}

	URISyntaxException errorURISyntax(String hostName, URISyntaxException e) {
		log.error("Syntax error for URI {}: {}", hostName,
				e.getLocalizedMessage());
		return e;
	}

}
