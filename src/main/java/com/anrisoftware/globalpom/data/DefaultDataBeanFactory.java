package com.anrisoftware.globalpom.data;

/**
 * Factory to create the default data bean.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface DefaultDataBeanFactory {

	/**
	 * Creates the default data bean from the specified data.
	 * 
	 * @param data
	 *            the {@link Data}.
	 * 
	 * @return the {@link DefaultDataBean}.
	 */
	DefaultDataBean create(Data data);
}
