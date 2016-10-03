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
