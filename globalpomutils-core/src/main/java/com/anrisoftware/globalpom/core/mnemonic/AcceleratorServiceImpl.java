package com.anrisoftware.globalpom.core.mnemonic;

import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Accelerator key service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = AcceleratorService.class)
public class AcceleratorServiceImpl implements AcceleratorService {

    @Inject
    private AcceleratorFactory factory;

    @Override
    public Accelerator create(String string) {
        return factory.create(string);
    }

    @Activate
    protected void start() {
        createInjector(new MnemonicModule()).injectMembers(this);
    }

}
