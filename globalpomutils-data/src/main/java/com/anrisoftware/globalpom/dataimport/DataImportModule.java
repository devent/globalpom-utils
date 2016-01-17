/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-data.
 *
 * globalpomutils-data is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-data is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-data. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.dataimport;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs data import factory.
 *
 * @see CharColumnFactory
 * @see StringColumnFactory
 * @see TypedColumnFactory
 * @see BooleanColumnFactory
 * @see DoubleColumnFactory
 * @see FloatColumnFactory
 * @see IntegerColumnFactory
 * @see LongColumnFactory
 * @see ShortColumnFactory
 * @see BigDecimalColumnFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public class DataImportModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(Column.class,
                StringColumn.class).build(StringColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                TypedColumn.class).build(TypedColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                BooleanColumn.class).build(BooleanColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                DoubleColumn.class).build(DoubleColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                FloatColumn.class).build(FloatColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                IntegerColumn.class).build(IntegerColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                LongColumn.class).build(LongColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                ShortColumn.class).build(ShortColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                BigDecimalColumn.class).build(BigDecimalColumnFactory.class));
        install(new FactoryModuleBuilder().implement(Column.class,
                CharColumn.class).build(CharColumnFactory.class));
    }

}
