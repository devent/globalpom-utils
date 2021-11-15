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
package com.anrisoftware.globalpom.core.inetsocketaddressformat;


import static com.anrisoftware.globalpom.core.inetsocketaddressformat.InetSocketAddressFormatLogger.m.errormparse;
import static com.anrisoftware.globalpom.core.inetsocketaddressformat.InetSocketAddressFormatLogger.m.errormparse1;
import static com.anrisoftware.globalpom.core.inetsocketaddressformat.InetSocketAddressFormatLogger.m.errormsyntax;
import static com.anrisoftware.globalpom.core.inetsocketaddressformat.InetSocketAddressFormatLogger.m.nomhostmport;
import static com.anrisoftware.globalpom.core.inetsocketaddressformat.InetSocketAddressFormatLogger.m.nomhostmport1;
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

    enum m {

        errormsyntax("Syntax error for URI {}: {}"),

        nomhostmport1("URI '{}' must have a host part."),

        nomhostmport("URI must have a host part"),

        errormparse1("Unparseable socket address: '{}'"),

        errormparse("Unparseable socket address: '%s'");

        private String name;

        private m(String name) {
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
        return logException(new ParseException(format(errormparse.toString(), source), pos.getErrorIndex()),
                errormparse1, source);
    }

    URISyntaxException errorURISyntax(URI uri) {
        return logException(new URISyntaxException(uri.toString(), nomhostmport.toString()), nomhostmport1, uri);
    }

    URISyntaxException errorURISyntax(String hostName, URISyntaxException e) {
        error(errormsyntax, hostName, e.getLocalizedMessage());
        return e;
    }

}
