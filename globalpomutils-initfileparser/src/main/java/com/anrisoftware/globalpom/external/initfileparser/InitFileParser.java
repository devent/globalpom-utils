/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-initfileparser.
 *
 * globalpomutils-initfileparser is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-initfileparser is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-initfileparser. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.external.initfileparser;

import java.util.Iterator;
import java.util.concurrent.Callable;

/**
 * Parses INI files.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public interface InitFileParser extends Callable<InitFileParser>,
        Iterable<Section> {

    /**
     * Loads the INI file to parsing it.
     * 
     * @return this {@link InitFileParser}
     * 
     * @throws InitFileParserException
     *             when an error opening or reading the INI file was encounted.
     */
    @Override
    InitFileParser call() throws InitFileParserException;

    /**
     * Parses and returns the sections of the INI file.
     * 
     * @return the {@link Iterator} that returns the {@link Section} sections.
     */
    @Override
    Iterator<Section> iterator();
}
