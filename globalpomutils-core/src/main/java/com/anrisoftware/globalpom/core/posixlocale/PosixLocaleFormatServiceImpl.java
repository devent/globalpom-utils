/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.posixlocale;


import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import com.anrisoftware.globalpom.core.localeformat.LocaleFormatModule;

/**
 * POSIX locale format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = PosixLocaleFormatService.class)
public class PosixLocaleFormatServiceImpl implements PosixLocaleFormatService {

    @Inject
    private PosixLocaleFormatFactory formatFactory;

    @Override
    public PosixLocaleFormat create() {
        return formatFactory.create();
    }

    @Activate
    protected void start() {
        createInjector(new PosixLocaleModule(), new LocaleFormatModule(), new PosixLocaleFormatModule())
                .injectMembers(this);
    }

}
