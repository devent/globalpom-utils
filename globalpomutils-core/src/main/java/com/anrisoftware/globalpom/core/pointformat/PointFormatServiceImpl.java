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
package com.anrisoftware.globalpom.core.pointformat;


import static com.google.inject.Guice.createInjector;

import java.text.NumberFormat;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Simple locale format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = PointFormatService.class)
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
