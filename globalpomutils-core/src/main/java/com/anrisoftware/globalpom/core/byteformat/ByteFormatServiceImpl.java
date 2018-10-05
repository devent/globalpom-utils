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
package com.anrisoftware.globalpom.core.byteformat;

import static com.google.inject.Guice.createInjector;

import java.text.NumberFormat;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Computer byte format service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = ByteFormatService.class)
public class ByteFormatServiceImpl implements ByteFormatService {

    @Inject
    private ByteFormatFactory byteFormatFactory;

    @Override
    public ByteFormat create() {
        return byteFormatFactory.create();
    }

    @Override
    public ByteFormat create(NumberFormat format) {
        return byteFormatFactory.create(format);
    }

    @Activate
    protected void start() {
        createInjector(new ByteFormatModule()).injectMembers(this);
    }

}
