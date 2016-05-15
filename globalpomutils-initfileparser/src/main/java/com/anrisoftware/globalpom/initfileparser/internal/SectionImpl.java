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

import java.io.Serializable;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.globalpom.initfileparser.external.Section;
import com.google.inject.assistedinject.Assisted;

/**
 * Section.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@SuppressWarnings("serial")
class SectionImpl implements Section, Serializable {

    private static final String NAME = "name";

    private final String name;

    private final Properties properties;

    @Inject
    SectionImpl(@Assisted String name, @Assisted Properties properties) {
        this.name = name;
        this.properties = properties;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(NAME, name).append(properties)
                .toString();
    }
}
