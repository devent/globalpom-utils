/*
 * Copyright 2014-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.reflection.beans;


import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link BeanAccessImpl}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.4
 */
class BeanFactoryImplLogger extends AbstractLogger {

    enum m {

        name_("name"),

        find_message("Cannot find the class '{}'."),

        find("Cannot find the class"),

        instantiate_message("Can not instantiate {}."),

        instantiate("Can not instantiate"),

        no_standard_message("No standard constructor found for {}."),

        no_standard("No standard constructor found"),

        exception_thrown_message("Exception thrown in the standard constructor of {}."),

        exception_thrown("Exception thrown in the standard constructor"),

        type("type"),

        illegal_access("Illegal access to the standard constructor"),

        illegal_access_message("Illegal access to the standard constructor of {}.");

        private String name;

        private m(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Creates logger for {@link BeanAccessImpl}.
     */
    BeanFactoryImplLogger() {
        super(BeanAccessImpl.class);
    }

}
