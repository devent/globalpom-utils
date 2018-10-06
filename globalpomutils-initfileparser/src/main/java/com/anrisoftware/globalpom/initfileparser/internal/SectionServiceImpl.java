package com.anrisoftware.globalpom.initfileparser.internal;

/*-
 * #%L
 * Global POM Utilities :: Init File Parser
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static com.google.inject.Guice.createInjector;

import java.util.Properties;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.anrisoftware.globalpom.initfileparser.external.Section;
import com.anrisoftware.globalpom.initfileparser.external.SectionFactory;
import com.anrisoftware.globalpom.initfileparser.external.SectionService;

/**
 * INI file section service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = SectionService.class)
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
