/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-initfileparser.
 *
 * globalpomutils-initfileparser is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-initfileparser is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-initfileparser. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.initfileparser;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * INI file parser factory.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface InitFileParserFactory {

    InitFileParser create(URL url, InitFileAttributes attributes);

    InitFileParser create(URL url, InitFileAttributes attributes,
            Charset charset);

    InitFileParser create(URI uri, InitFileAttributes attributes);

    InitFileParser create(URI uri, InitFileAttributes attributes,
            Charset charset);

    InitFileParser create(File file, InitFileAttributes attributes);

    InitFileParser create(File file, InitFileAttributes attributes,
            Charset charset);
}
