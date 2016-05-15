/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-threads.
 *
 * globalpomutils-threads is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-threads is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-threads. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.threads.properties.internal;

import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreads;
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsFactory;
import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreadsService;

/**
 * Threads pool properties file service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(PropertiesThreadsService.class)
public class PropertiesThreadsServiceImpl implements PropertiesThreadsService {

    @Inject
    private PropertiesThreadsFactory factory;

    @Override
    public PropertiesThreads create() {
        return factory.create();
    }

    @Activate
    protected void start() {
        createInjector(new PropertiesThreadsModule()).injectMembers(this);
    }

}
