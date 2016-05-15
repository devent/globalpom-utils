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
package com.anrisoftware.globalpom.initfileparser.internal;

import static com.google.inject.Guice.createInjector;

import java.util.Properties;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.initfileparser.external.Section;
import com.anrisoftware.globalpom.initfileparser.external.SectionFactory;
import com.anrisoftware.globalpom.initfileparser.external.SectionService;

/**
 * INI file section service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(SectionService.class)
public class SectionServiceImpl implements SectionService {

    @Inject
    private SectionFactory factory;

    @Override
    public Section create(String name, Properties properties) {
        return factory.create(name, properties);
    }

    @Activate
    protected void start() {
        createInjector(new InitFileParserModule()).injectMembers(this);
    }

}
