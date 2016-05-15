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
package com.anrisoftware.globalpom.internal.initfileparser;

import static com.google.inject.Guice.createInjector;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.external.initfileparser.InitFileAttributes;
import com.anrisoftware.globalpom.external.initfileparser.InitFileParser;
import com.anrisoftware.globalpom.external.initfileparser.InitFileParserFactory;
import com.anrisoftware.globalpom.external.initfileparser.InitFileParserService;

/**
 * INI file parser service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(InitFileParserService.class)
public class InitFileParserServiceImpl implements InitFileParserService {

    @Inject
    private InitFileParserFactory factory;

    @Override
    public InitFileParser create(URL url, InitFileAttributes attributes) {
        return factory.create(url, attributes);
    }

    @Override
    public InitFileParser create(URL url, InitFileAttributes attributes,
            Charset charset) {
        return factory.create(url, attributes, charset);
    }

    @Override
    public InitFileParser create(URI uri, InitFileAttributes attributes) {
        return factory.create(uri, attributes);
    }

    @Override
    public InitFileParser create(URI uri, InitFileAttributes attributes,
            Charset charset) {
        return factory.create(uri, attributes, charset);
    }

    @Override
    public InitFileParser create(File file, InitFileAttributes attributes) {
        return factory.create(file, attributes);
    }

    @Override
    public InitFileParser create(File file, InitFileAttributes attributes,
            Charset charset) {
        return factory.create(file, attributes, charset);
    }

    @Activate
    protected void start() {
        createInjector(new InitFileParserModule()).injectMembers(this);
    }

}
