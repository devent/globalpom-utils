/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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

/**
 * Factory to create a new worker that replace search text with a replacement.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.0
 */
public interface DefaultTokensTemplateFactory {

    /**
     * Creates a new worker that replace the specified search text with a
     * replacement.
     * 
     * @param tokenMarker
     *            the {@link TokenMarker} holding the begin and end tokens.
     * 
     * @param template
     *            the {@link TokenTemplate} containing the search text and the
     *            replacement.
     * 
     * @param text
     *            the text in which to replace.
     * 
     * @return the {@link DefaultTokensTemplate}.
     */
    DefaultTokensTemplate create(TokenMarker tokenMarker,
            TokenTemplate template, String text);

}
