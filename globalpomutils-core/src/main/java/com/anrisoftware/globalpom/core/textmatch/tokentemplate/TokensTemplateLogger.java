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
package com.anrisoftware.globalpom.core.textmatch.tokentemplate;

/*-
 * #%L
 * Global POM Utilities :: Core
 * %%
 * Copyright (C) 2013 - 2018 Advanced Natural Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static com.anrisoftware.globalpom.core.textmatch.tokentemplate.TokensTemplateLogger._.argument_append;
import static com.anrisoftware.globalpom.core.textmatch.tokentemplate.TokensTemplateLogger._.argument_replaced;

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
