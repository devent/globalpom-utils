package com.anrisoftware.globalpom.data;

/**
 * Data property.
 * 
 * @see DataBean
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public enum DataProperty {

	/**
	 * The number of columns property.
	 * 
	 * @see DataBean#reshape(int, int)
	 * @see DataBean#reshape(int, int, boolean)
	 * @see DataBean#setNumCols(int)
	 */
	COLUMNS,

	/**
	 * The number of rows property.
	 * 
	 * @see DataBean#reshape(int, int)
	 * @see DataBean#reshape(int, int, boolean)
	 * @see DataBean#setNumRows(int)
	 */
	ROWS,

	/**
	 * The data property.
	 * 
	 * @see DataBean#set(org.ejml.data.Matrix64F)
	 * @see DataBean#set(int, int, double)
	 */
	DATA
}
