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
package com.anrisoftware.globalpom.strings;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

/**
 * Converts an argument to a String.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Component
@Service(ToStringService.class)
public class ToStringServiceImpl implements ToStringService {

    @Override
    public String toString(Map<String, Object> args, String name) {
        Object value = args.get(name);
        return toString(value, name);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String toString(Object arg, String name) {
        notNull(arg, "arg = null");
        return ObjectUtils.toString(arg);
    }

}
