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
package com.anrisoftware.globalpom.textmatch.match;

import static com.google.inject.Guice.createInjector;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

/**
 * Text matcher service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(MatchTextService.class)
public class MatchTextServiceImpl implements MatchTextService {

    @Inject
    private MatchTextFactory factory;

    @Override
    public MatchText create(File file, Pattern pattern, Charset charset) {
        return factory.create(file, pattern, charset);
    }

    @Override
    public MatchText create(URL resource, Pattern pattern,
            Charset charset) throws URISyntaxException {
        return factory.create(resource, pattern, charset);
    }

    @Override
    public MatchText create(URI resource, Pattern pattern,
            Charset charset) {
        return factory.create(resource, pattern, charset);
    }

    @Activate
    protected void start() {
        createInjector(new MatchTextModule()).injectMembers(this);
    }

}
