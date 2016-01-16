/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-projects.
 *
 * globalpomutils-projects is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-projects is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-projects. If not, see <http://www.gnu.org/licenses/>.
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
