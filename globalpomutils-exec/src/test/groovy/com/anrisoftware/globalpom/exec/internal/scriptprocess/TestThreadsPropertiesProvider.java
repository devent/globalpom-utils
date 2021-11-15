/*
 * Copyright 2014-2021 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.exec.internal.scriptprocess;

import java.net.URL;

import com.anrisoftware.globalpom.threads.properties.external.PropertiesThreads;
import com.anrisoftware.propertiesutils.AbstractContextPropertiesProvider;

/**
 * Returns properties for test threads pool.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.3
 */
@SuppressWarnings("serial")
public class TestThreadsPropertiesProvider extends AbstractContextPropertiesProvider {

    private static final URL RES = TestThreadsPropertiesProvider.class.getResource("test_threads.properties");

    TestThreadsPropertiesProvider() {
        super(PropertiesThreads.class, RES);
    }

}
