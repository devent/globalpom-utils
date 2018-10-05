package com.anrisoftware.globalpom.core.inetsocketaddressformat;

import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Internet socked address formatter service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = InetSocketAddressFormatService.class)
public class InetSocketAddressFormatServiceImpl implements InetSocketAddressFormatService {

    @Inject
    private InetSocketAddressFormatFactory formatFactory;

    @Override
    public InetSocketAddressFormat create() {
        return formatFactory.create();
    }

    @Activate
    protected void start() {
        createInjector(new InetSocketAddressFormatModule()).injectMembers(this);
    }

}
