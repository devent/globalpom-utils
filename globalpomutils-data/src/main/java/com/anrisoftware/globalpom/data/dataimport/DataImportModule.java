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
package com.anrisoftware.globalpom.data.dataimport;

/*-
 * #%L
 * Global POM Utilities :: Data
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
