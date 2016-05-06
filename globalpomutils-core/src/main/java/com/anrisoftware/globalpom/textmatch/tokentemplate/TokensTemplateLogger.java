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
package com.anrisoftware.globalpom.textmatch.tokentemplate;

import static com.anrisoftware.globalpom.textmatch.tokentemplate.TokensTemplateLogger._.argument_append;
import static com.anrisoftware.globalpom.textmatch.tokentemplate.TokensTemplateLogger._.argument_replaced;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging messages for {@link TokensTemplate}.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
class TokensTemplateLogger extends AbstractLogger {

    enum _ {

        argument_append("Argument appended in {}."),

        argument_replaced("Argument replaced in {}.");

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
     * Create logger for {@link TokensTemplate}.
     */
    public TokensTemplateLogger() {
        super(TokensTemplate.class);
    }

    void replacedArgument(TokensTemplate worker) {
        debug(argument_replaced, worker);
    }

    void appendArgument(TokensTemplate worker) {
        debug(argument_append, worker);
    }

}
