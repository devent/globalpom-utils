/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

import javax.inject.Inject;

/**
 * Parses an Internet socket address.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
public class InetSocketAddressFormat extends Format {

    /**
     * @see #create()
     *
     * @return the Internet socket address {@link InetSocketAddressFormat}.
     */
    public static InetSocketAddressFormat createInetSocketAddressFormat() {
        return create();
    }

    /**
     * Creates the Internet socket address formatter.
     *
     * @return the Internet socket address {@link InetSocketAddressFormat}.
     */
    public static InetSocketAddressFormat create() {
        return InetSocketAddressFormatModule.getFactory().create();
    }

    @Inject
    private InetSocketAddressFormatLogger log;

    /**
     * Formats the specified Internet socket address. The format follows the
     * patterns:
     *
     * <ul>
     * <li>{@code <ip-address>}</li>
     * <li>{@code <ip-address>:<port-number>}</li>
     * <li>{@code <host-name>}</li>
     * <li>{@code <host-name>:<port-number>}</li>
     * </ul>
     *
     * @param obj the {@link InetSocketAddress}.
     */
    @Override
    public StringBuffer format(Object obj, StringBuffer buff, FieldPosition pos) {
        if (obj instanceof InetSocketAddress) {
            InetSocketAddress address = (InetSocketAddress) obj;
            formatAddress(buff, address);
        }
        return buff;
    }

    private void formatAddress(StringBuffer buff, InetSocketAddress address) {
        buff.append(address.getHostString());
        if (address.getPort() > 0) {
            buff.append(":");
            buff.append(address.getPort());
        }
    }

    /**
     * Parses the specified string to an Internet socket address.
     * <p>
     * The parser expects the patterns:
     *
     * <ul>
     * <li>{@code <ip-address>}</li>
     * <li>{@code <ip-address>:<port-number>}</li>
     * <li>{@code <host-name>}</li>
     * <li>{@code <host-name>:<port-number>}</li>
     * </ul>
     */
    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * Parses the specified string to an Internet socket address.
     * <p>
     * The parser expects the patterns:
     *
     * <ul>
     * <li>{@code <ip-address>}</li>
     * <li>{@code <ip-address>:<port-number>}</li>
     * <li>{@code <host-name>}</li>
     * <li>{@code <host-name>:<port-number>}</li>
     * </ul>
     *
     * @return the parsed {@link InetSocketAddress}.
     *
     * @throws ParseException if the string does not have IP address, host name or
     *                        port number.
     */
    public InetSocketAddress parse(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        InetSocketAddress result = parse(source, pos);
        if (pos.getIndex() == 0) {
            throw log.errorParseAddress(source, pos);
        }
        return result;
    }

    /**
     * Parses the specified string from the specified index to an Internet socket
     * address.
     * <p>
     * The parser expects the patterns:
     *
     * <ul>
     * <li>{@code <ip-address>}</li>
     * <li>{@code <ip-address>:<port-number>}</li>
     * <li>{@code <host-name>}</li>
     * <li>{@code <host-name>:<port-number>}</li>
     * </ul>
     *
     * @param source the source string.
     *
     * @param pos    the index {@link ParsePosition} position from where to start
     *               parsing.
     *
     * @return the parsed {@link InetSocketAddress}.
     *
     * @throws ParseException if the string does not have IP address, host name or
     *                        port number.
     */
    public InetSocketAddress parse(String source, ParsePosition pos) {
        try {
            source = source.substring(pos.getIndex());
            InetSocketAddress address = parseHost(source);
            pos.setErrorIndex(-1);
            pos.setIndex(source.length());
            return address;
        } catch (URISyntaxException e) {
            pos.setIndex(0);
            pos.setErrorIndex(0);
            return null;
        }
    }

    private InetSocketAddress parseHost(String hostName) throws URISyntaxException {
        String host;
        int port;
        try {
            URI uri = new URI("my://" + hostName);
            host = uri.getHost();
            port = uri.getPort();
            port = port == -1 ? 0 : port;
            if (uri.getHost() == null) {
                throw log.errorURISyntax(uri);
            }
            return new InetSocketAddress(host, port);
        } catch (URISyntaxException e) {
            throw log.errorURISyntax(hostName, e);
        }
    }

}
