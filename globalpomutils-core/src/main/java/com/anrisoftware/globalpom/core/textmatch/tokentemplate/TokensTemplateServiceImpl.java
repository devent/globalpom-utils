package com.anrisoftware.globalpom.core.textmatch.tokentemplate;

import static com.google.inject.Guice.createInjector;

import javax.inject.Inject;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * Tokens template service.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 3.1
 */
@Component(service = TokensTemplateService.class)
public class TokensTemplateServiceImpl implements TokensTemplateService {

    @Inject
    private TokensTemplateFactory factory;

    @Override
    public TokensTemplate create(TokenMarker tokenMarker, TokenTemplate template, String text) {
        return factory.create(tokenMarker, template, text);
    }

    @Activate
    protected void start() {
        createInjector(new TokensTemplateModule()).injectMembers(this);
    }

}
