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

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.initfileparser.external.DefaultInitFileAttributes;
import com.anrisoftware.globalpom.initfileparser.external.DefaultInitFileAttributesFactory;
import com.anrisoftware.globalpom.initfileparser.external.DefaultInitFileAttributesService;
import com.anrisoftware.globalpom.initfileparser.external.InitFileAttributes;

/**
 * Mutable INI file attributes service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(DefaultInitFileAttributesService.class)
public class DefaultInitFileAttributesServiceImpl implements
        DefaultInitFileAttributesService {

    @Inject
    private DefaultInitFileAttributesFactory factory;

    @Override
    public DefaultInitFileAttributes create() {
        return factory.create();
    }

    @Override
    public DefaultInitFileAttributes create(InitFileAttributes attributes) {
        return factory.create(attributes);
    }

    @Activate
    protected void start() {
        createInjector(new InitFileParserModule()).injectMembers(this);
    }

}
