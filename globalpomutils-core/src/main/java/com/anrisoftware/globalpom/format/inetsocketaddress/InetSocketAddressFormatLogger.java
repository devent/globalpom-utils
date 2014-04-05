/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.format.inetsocketaddress;

import static com.anrisoftware.globalpom.format.inetsocketaddress.InetSocketAddressFormatLogger._.error_parse;
import static com.anrisoftware.globalpom.format.inetsocketaddress.InetSocketAddressFormatLogger._.error_parse1;
import static com.anrisoftware.globalpom.format.inetsocketaddress.InetSocketAddressFormatLogger._.error_syntax;
import static com.anrisoftware.globalpom.format.inetsocketaddress.InetSocketAddressFormatLogger._.no_host_port;
import static com.anrisoftware.globalpom.format.inetsocketaddress.InetSocketAddressFormatLogger._.no_host_port1;
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

	enum _ {

		error_syntax("Syntax error for URI {}: {}"),

		no_host_port1("URI '{}' must have a host part."),

		no_host_port("URI must have a host part"),

		error_parse1("Unparseable socket address: '{}'"),

		error_parse("Unparseable socket address: '%s'");

		private String name;

		private _(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	/**
	 * Create logger for {@link InetSocketAddressFormat}.
	 */
	InetSocketAddressFormatLogger() {
		super(InetSocketAddressFormat.class);
	}

	ParseException errorParseAddress(String source, ParsePosition pos) {
		return logException(
				new ParseException(format(error_parse.toString(), source),
						pos.getErrorIndex()), error_parse1, source);
	}

	URISyntaxException errorURISyntax(URI uri) {
		return logException(
				new URISyntaxException(uri.toString(), no_host_port.toString()),
				no_host_port1, uri);
	}

	URISyntaxException errorURISyntax(String hostName, URISyntaxException e) {
		error(error_syntax, hostName, e.getLocalizedMessage());
		return e;
	}

}
