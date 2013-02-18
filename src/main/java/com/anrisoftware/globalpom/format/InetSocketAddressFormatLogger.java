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
