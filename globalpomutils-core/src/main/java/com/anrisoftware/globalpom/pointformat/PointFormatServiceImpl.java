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
package com.anrisoftware.globalpom.pointformat;

import static com.google.inject.Guice.createInjector;

import java.text.NumberFormat;

import javax.inject.Inject;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

/**
 * Simple locale format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component
@Service(PointFormatService.class)
public class PointFormatServiceImpl implements PointFormatService {

    @Inject
    private PointFormatFactory formatFactory;

    @Override
    public PointFormat defaultFormat() {
        return formatFactory.defaultFormat();
    }

    @Override
    public PointFormat create(NumberFormat format) {
        return formatFactory.create(format);
    }

    @Activate
    protected void start() {
        createInjector(new PointFormatModule()).injectMembers(this);
    }

}
