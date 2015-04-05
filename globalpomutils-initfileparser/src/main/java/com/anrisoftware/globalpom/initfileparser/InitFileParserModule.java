/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
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
