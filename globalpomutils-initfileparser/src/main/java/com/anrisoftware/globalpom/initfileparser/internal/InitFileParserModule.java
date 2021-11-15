/*
 * Copyright 2013-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import com.anrisoftware.globalpom.initfileparser.external.DefaultInitFileAttributes;
import com.anrisoftware.globalpom.initfileparser.external.DefaultInitFileAttributesFactory;
import com.anrisoftware.globalpom.initfileparser.external.InitFileParser;
import com.anrisoftware.globalpom.initfileparser.external.InitFileParserFactory;
import com.anrisoftware.globalpom.initfileparser.external.Section;
import com.anrisoftware.globalpom.initfileparser.external.SectionFactory;
import com.anrisoftware.globalpom.initfileparser.external.SectionFormatter;
import com.anrisoftware.globalpom.initfileparser.external.SectionFormatterFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the INI file parser.
 * 
 * @see InitFileParserFactory
 * @see DefaultInitFileAttributesFactory
 * @see SectionFormatterFactory
 * @see SectionFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class InitFileParserModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(InitFileParser.class,
                InitFileParserImpl.class).build(InitFileParserFactory.class));
        install(new FactoryModuleBuilder().implement(Section.class,
                SectionImpl.class).build(SectionFactory.class));
        install(new FactoryModuleBuilder().implement(
                DefaultInitFileAttributes.class,
                DefaultInitFileAttributes.class).build(
                DefaultInitFileAttributesFactory.class));
        install(new FactoryModuleBuilder().implement(SectionFormatter.class,
                SectionFormatterImpl.class)
                .build(SectionFormatterFactory.class));
    }

}
