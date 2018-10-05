package com.anrisoftware.globalpom.core.strings;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.osgi.service.component.annotations.Component;

/**
 * Converts an argument to a String.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Component(service = ToStringService.class)
public class ToStringServiceImpl implements ToStringService {

    @Override
    public String toString(Map<String, Object> args, String name) {
        Object value = args.get(name);
        return toString(value, name);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String toString(Object arg, String name) {
        notNull(arg, "arg = null");
        return ObjectUtils.toString(arg);
    }

}
