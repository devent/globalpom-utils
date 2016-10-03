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
package com.anrisoftware.globalpom.initfileparser.internal;

import static com.google.inject.Guice.createInjector;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.initfileparser.external.InitFileAttributes;
import com.anrisoftware.globalpom.initfileparser.external.InitFileParser;
import com.anrisoftware.globalpom.initfileparser.external.InitFileParserFactory;
import com.anrisoftware.globalpom.initfileparser.external.InitFileParserService;

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
