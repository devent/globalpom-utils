package com.anrisoftware.globalpom.mnemonic;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the mnemonic factory.
 * 
 * @see Mnemonic
 * @see MnemonicFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class MnemonicModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(Mnemonic.class,
				Mnemonic.class).build(MnemonicFactory.class));
	}

}
