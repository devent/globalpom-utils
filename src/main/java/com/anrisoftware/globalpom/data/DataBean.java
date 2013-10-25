package com.anrisoftware.globalpom.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Informs property change listeners of change in the dimension or data.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface DataBean extends Data {

	/**
	 * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
	 * @see DataProperty
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
	 * @see DataProperty
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * @see PropertyChangeSupport#addPropertyChangeListener(String,
	 *      PropertyChangeListener)
	 * @see DataProperty
	 */
	void addPropertyChangeListener(DataProperty property,
			PropertyChangeListener listener);

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(String,
	 *      PropertyChangeListener)
	 * @see DataProperty
	 */
	void removePropertyChangeListener(DataProperty property,
			PropertyChangeListener listener);

}
