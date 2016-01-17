/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-math.
 *
 * globalpomutils-math is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-math is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-math. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.distribution.core;

import com.anrisoftware.globalpom.distribution.range.DistributionRangeModule;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Core distribution module.
 *
 * @see DistributionValueFactory
 * @see DistributionBeanFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.14
 */
public class DistributionCoreModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new DistributionRangeModule());
        install(new FactoryModuleBuilder().implement(DistributionBin.class,
                DistributionBin.class).build(DistributionBinFactory.class));
        install(new FactoryModuleBuilder().implement(
                DefaultDistributionBean.class, DefaultDistributionBean.class)
                .build(DistributionBeanFactory.class));
    }

}
