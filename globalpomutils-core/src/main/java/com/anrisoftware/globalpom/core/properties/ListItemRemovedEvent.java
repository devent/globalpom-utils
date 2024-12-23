/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.properties;

/**
 * Event when a list item was removed.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class ListItemRemovedEvent extends ListPropertyChangeEvent {

    /**
     * @see ListPropertyChangeEvent#ListPropertyChangeEvent(Object, String, int,
     *      int, Object, Object)
     *
     * @param source       the source.
     *
     * @param propertyName the {@link String} property name.
     *
     * @param index0       the first index where the list was changed.
     *
     * @param index1       the last index where the list was changed.
     *
     * @param oldValue     the old value.
     */
    public ListItemRemovedEvent(Object source, String propertyName, int index0, int index1, Object oldValue) {
        super(source, propertyName, index0, index1, oldValue, null);
    }

    @Override
    public ListPropertyChange getType() {
        return ListPropertyChange.REMOVED;
    }
}
