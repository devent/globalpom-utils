/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
     * Formats the specified Internet socket address.
     * <p>
     * The format follows the patterns:
     * 
     * <ul>
     * <li>{@code <ip-address>}</li>
     * <li>{@code <ip-address>:<port-number>}</li>
     * <li>{@code <host-name>}</li>
     * <li>{@code <host-name>:<port-number>}</li>
     * </ul>
     * 
     * @param obj
     *            the {@link InetSocketAddress}.
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
     * @throws ParseException
     *             if the string does not have IP address, host name or port
     *             number.
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
     * Parses the specified string from the specified index to an Internet
     * socket address.
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
     * @param source
     *            the source string.
     * 
     * @param pos
     *            the index {@link ParsePosition} position from where to start
     *            parsing.
     * 
     * @return the parsed {@link InetSocketAddress}.
     * 
     * @throws ParseException
     *             if the string does not have IP address, host name or port
     *             number.
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

    private InetSocketAddress parseHost(String hostName)
            throws URISyntaxException {
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
