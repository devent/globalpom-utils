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
package com.anrisoftware.globalpom.posixlocale;

import static com.google.inject.Guice.createInjector;

import java.nio.charset.Charset;
import java.util.Locale;

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
@Service(PosixLocaleService.class)
public class PosixLocaleServiceImpl implements PosixLocaleService {

    @Inject
    private PosixLocaleFactory localeFactory;

    @Override
    public PosixLocale create(Locale locale) {
        return localeFactory.create(locale);
    }

    @Override
    public PosixLocale create(Locale locale, Charset charset) {
        return localeFactory.create(locale, charset);
    }

    @Activate
    protected void start() {
        createInjector(new PosixLocaleModule()).injectMembers(this);
    }

}
