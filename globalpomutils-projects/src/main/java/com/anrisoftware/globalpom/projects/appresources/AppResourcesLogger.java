/**
 * Copyright © 2013 Erwin Müller (erwin.mueller@anrisoftware.com)
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

import static com.anrisoftware.globalpom.projects.appresources.AppResourcesLogger._.error_property_change;
import static com.anrisoftware.globalpom.projects.appresources.AppResourcesLogger._.error_save_resources;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link AbstractAppResources}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
class AppResourcesLogger extends AbstractLogger {

    enum _ {

        error_property_change("Error in property change"),

        error_property_change_message("Error in property '{}' change: {}"),

        the_property("property"),

        the_old_value("old value"),

        the_value("value"),

        error_save_resources("Error save application resources");

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
     * Sets the context of the logger to {@link AbstractAppResources}.
     */
    public AppResourcesLogger() {
        super(AbstractAppResources.class);
    }

    void errorPropertyChange(Object property, Object old, Object v, Throwable e) {
        PropertyChangeException ex = new PropertyChangeException(
                error_property_change, e, property, old, v);
        log.error(ex.getLocalizedMessage(), ex);
        throw ex;
    }

    void errorSaveResources(Throwable e) {
        log.error(error_save_resources.toString(), e);
    }
}
