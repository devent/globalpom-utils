package com.anrisoftware.globalpom.core.mnemonic;

import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Mnemonic service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = MnemonicService.class)
public class MnemonicServiceImpl implements MnemonicService {

    @Inject
    private MnemonicFactory factory;

    @Override
    public Mnemonic create(String string) {
        return factory.create(string);
    }

    @Activate
    protected void start() {
        createInjector(new MnemonicModule()).injectMembers(this);
    }

}
