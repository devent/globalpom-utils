package com.anrisoftware.globalpom.data;

import org.ejml.data.Matrix64F;

/**
 * Factory to create new matrix data.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public interface MatrixDataFactory {

	/**
	 * Creates new matrix data with the specified matrix.
	 * 
	 * @param matrix
	 *            the {@link Matrix64F}
	 * 
	 * @return the {@link MatrixData}.
	 */
	MatrixData create(Matrix64F matrix);
}
