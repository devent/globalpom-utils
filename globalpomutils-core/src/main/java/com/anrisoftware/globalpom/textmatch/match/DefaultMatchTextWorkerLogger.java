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
package com.anrisoftware.globalpom.textmatch.match;

import static com.anrisoftware.globalpom.textmatch.match.DefaultMatchTextWorkerLogger._.error_read;
import static com.anrisoftware.globalpom.textmatch.match.DefaultMatchTextWorkerLogger._.error_read_message;
import static com.anrisoftware.globalpom.textmatch.match.DefaultMatchTextWorkerLogger._.no_search_text;
import static com.anrisoftware.globalpom.textmatch.match.DefaultMatchTextWorkerLogger._.search_text;
import static com.anrisoftware.globalpom.textmatch.match.DefaultMatchTextWorkerLogger._.worker_;

import java.io.IOException;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link DefaultMatchText}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
class DefaultMatchTextWorkerLogger extends AbstractLogger {

    enum _ {

        worker_("worker"),

        error_read_message("Error read resource '{}'"),

        error_read("Error read resource"),

        no_search_text("Search text was not found in {}."),

        search_text("Search text was found in {}.");

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
     * Create logger for {@link DefaultMatchText}.
     */
    public DefaultMatchTextWorkerLogger() {
        super(DefaultMatchText.class);
    }

    MatchTextException readResourceError(DefaultMatchText worker, IOException e) {
        return logException(
                new MatchTextException(error_read, e).add(worker_, worker),
                error_read_message, worker.getResource());
    }

    void textWasFound(DefaultMatchText worker) {
        debug(search_text, worker);
    }

    void textWasNotFound(DefaultMatchText worker) {
        debug(no_search_text, worker);
    }
}
