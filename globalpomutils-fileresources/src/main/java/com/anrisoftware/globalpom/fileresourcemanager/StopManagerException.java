/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-fileresources.
 *
 * globalpomutils-fileresources is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-fileresources is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-fileresources. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.fileresourcemanager;

import org.apache.commons.transaction.file.ResourceManagerSystemException;

@SuppressWarnings("serial")
public class StopManagerException extends FileResourceException {

    public StopManagerException(ResourceManagerSystemException e,
            String storeDir) {
        super("Error stop file resource manager", e);
        addContextValue("store-directory", storeDir);
    }

}
