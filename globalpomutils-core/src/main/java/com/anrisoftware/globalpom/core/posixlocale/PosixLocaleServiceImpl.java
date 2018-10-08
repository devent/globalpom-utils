package com.anrisoftware.globalpom.core.posixlocale;

/*-
 * #%L
 * Global POM Utilities :: Core
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

import java.nio.charset.Charset;
import java.util.Locale;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Simple locale format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = PosixLocaleService.class)
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