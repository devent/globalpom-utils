/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Wraps the begin and end token of the place holders.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public class TokenMarker {

    /**
     * The {@link String} token that marks the beginning of the template
     * parameter.
     */
    public final String beginToken;

    /**
     * The {@link String} token that marks the ending of the template parameter.
     */
    public final String endToken;

    /**
     * Sets the begin and end token.
     * 
     * @param beginToken
     *            the {@link String} token that marks the beginning of the
     *            template parameter.
     * 
     * @param endToken
     *            the {@link String} token that marks the ending of the template
     *            parameter.
     */
    public TokenMarker(String beginToken, String endToken) {
        this.beginToken = beginToken;
        this.endToken = endToken;
    }

    /**
     * Returns the token that marks the beginning of the template parameter.
     * 
     * @return the begin token.
     */
    public String getBeginToken() {
        return beginToken;
    }

    /**
     * Returns the token that marks the ending of the template parameter.
     * 
     * @return the end token.
     */
    public String getEndToken() {
        return endToken;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(beginToken).append(endToken)
                .toString();
    }

}
