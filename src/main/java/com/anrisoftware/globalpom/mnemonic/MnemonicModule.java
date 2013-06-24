package com.anrisoftware.globalpom.mnemonic;

import static com.google.inject.Guice.createInjector;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the mnemonic factory.
 * 
 * @see Mnemonic
 * @see MnemonicFactory
 * @see Accelerator
 * @see AcceleratorFactory
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.5
 */
public class MnemonicModule extends AbstractModule {

	private static Injector injector;

	/**
	 * Creates the Guice injector.
	 * 
	 * @return the {@link Injector}.
	 */
	public static Injector getInjector() {
		if (injector == null) {
			synchronized (MnemonicModule.class) {
				injector = createInjector(new MnemonicModule());
			}
		}
		return injector;
	}

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(Mnemonic.class,
				Mnemonic.class).build(MnemonicFactory.class));
		install(new FactoryModuleBuilder().implement(Accelerator.class,
				Accelerator.class).build(AcceleratorFactory.class));
	}

}
