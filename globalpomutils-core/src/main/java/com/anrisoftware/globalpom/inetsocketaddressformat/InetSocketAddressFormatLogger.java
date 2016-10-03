/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.inetsocketaddressformat;

import static com.anrisoftware.globalpom.inetsocketaddressformat.InetSocketAddressFormatLogger._.error_parse;
import static com.anrisoftware.globalpom.inetsocketaddressformat.InetSocketAddressFormatLogger._.error_parse1;
import static com.anrisoftware.globalpom.inetsocketaddressformat.InetSocketAddressFormatLogger._.error_syntax;
import static com.anrisoftware.globalpom.inetsocketaddressformat.InetSocketAddressFormatLogger._.no_host_port;
import static com.anrisoftware.globalpom.inetsocketaddressformat.InetSocketAddressFormatLogger._.no_host_port1;
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
