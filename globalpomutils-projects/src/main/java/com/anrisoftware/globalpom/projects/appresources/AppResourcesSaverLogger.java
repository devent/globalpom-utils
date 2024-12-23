/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.projects.appresources;

import static com.anrisoftware.globalpom.projects.appresources.AppResourcesSaverLogger._.resources_saved;

import java.io.File;

import jakarta.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link AppResourcesSaver}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
final class AppResourcesSaverLogger extends AbstractLogger {

    enum _ {

        resources_saved("Application resources saved in {}.");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Sets the context of the logger to {@link AppResourcesSaver}.
     */
    public AppResourcesSaverLogger() {
        super(AppResourcesSaver.class);
    }

    void resourcesSaved(File storeDir) {
        debug(resources_saved, storeDir);
    }
}
