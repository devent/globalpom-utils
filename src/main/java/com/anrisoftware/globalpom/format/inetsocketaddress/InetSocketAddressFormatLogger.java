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
package com.anrisoftware.globalpom.format.inetsocketaddress;

import static java.lang.String.format;

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

	private static final String SYNTAX_ERROR = "Syntax error for URI {}: {}";
	private static final String URI_HOST_PART_MESSAGE = "URI '{}' must have a host part.";
	private static final String URI_HOST_PART = "URI must have a host part";
	private static final String UNPARSEABLE_SOCKET_ADDRESS_MESSAGE = "Unparseable socket address: '{}'";
	private static final String UNPARSEABLE_SOCKET_ADDRESS = "Unparseable socket address: '%s'";

	/**
	 * Create logger for {@link InetSocketAddressFormat}.
	 */
	InetSocketAddressFormatLogger() {
		super(InetSocketAddressFormat.class);
	}

	ParseException errorParseAddress(String source, ParsePosition pos) {
		return logException(
				new ParseException(format(UNPARSEABLE_SOCKET_ADDRESS, source),
						pos.getErrorIndex()),
				UNPARSEABLE_SOCKET_ADDRESS_MESSAGE, source);
	}

	URISyntaxException errorURISyntax(URI uri) {
		return logException(new URISyntaxException(uri.toString(),
				URI_HOST_PART), URI_HOST_PART_MESSAGE, uri);
	}

	URISyntaxException errorURISyntax(String hostName, URISyntaxException e) {
		log.error(SYNTAX_ERROR, hostName, e.getLocalizedMessage());
		return e;
	}

}
