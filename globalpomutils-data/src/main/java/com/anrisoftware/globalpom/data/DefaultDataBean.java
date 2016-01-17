/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-data.
 *
 * globalpomutils-data is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-data is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-data. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.data;

import static com.anrisoftware.globalpom.data.DataProperty.COLUMNS;
import static com.anrisoftware.globalpom.data.DataProperty.DATA;
import static com.anrisoftware.globalpom.data.DataProperty.ROWS;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javax.inject.Inject;

import org.ejml.data.MatrixIterator;
import org.ejml.data.ReshapeMatrix64F;

import com.anrisoftware.globalpom.properties.ListPropertyChangeSupport;
import com.google.inject.assistedinject.Assisted;

/**
 * Informs property change listeners of change in the dimension or data.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
@SuppressWarnings("serial")
public class DefaultDataBean implements DataBean, Serializable {

    private final Data data;

    private transient PropertyChangeSupport p;

    private transient ListPropertyChangeSupport lp;

    /**
     * @see DefaultDataBeanFactory#create(Data)
     */
    @Inject
    DefaultDataBean(@Assisted Data data) {
        this.data = data;
        readResolve();
    }

    private Object readResolve() {
        this.p = new PropertyChangeSupport(this);
        this.lp = new ListPropertyChangeSupport(this, p);
        return this;
    }

    @Override
    public ReshapeMatrix64F getMatrix() {
        return data.getMatrix();
    }

    @Override
    public void reshape(int numRows, int numCols, boolean saveValues) {
        int oldNumRows = getNumRows();
        int oldNumCols = getNumCols();
        data.reshape(numRows, numCols, saveValues);
        p.firePropertyChange(COLUMNS.toString(), oldNumCols, numCols);
        p.firePropertyChange(ROWS.toString(), oldNumRows, numRows);
    }

    @Override
    public void reshape(int numRows, int numCols) {
        int oldNumRows = getNumRows();
        int oldNumCols = getNumCols();
        data.reshape(numRows, numCols);
        p.firePropertyChange(COLUMNS.toString(), oldNumCols, numCols);
        p.firePropertyChange(ROWS.toString(), oldNumRows, numRows);
    }

    @Override
    public double get(int row, int col) {
        return data.get(row, col);
    }

    @Override
    public double unsafe_get(int row, int col) {
        return data.unsafe_get(row, col);
    }

    @Override
    public void set(int row, int col, double val) {
        double oldValue = unsafe_get(row, col);
        data.set(row, col, val);
        lp.fireListItemChangedChange(DATA, row, row, oldValue, val);
    }

    @Override
    public void unsafe_set(int row, int col, double val) {
        double oldValue = unsafe_get(row, col);
        data.unsafe_set(row, col, val);
        lp.fireListItemChangedChange(DATA, row, row, oldValue, val);
    }

    @Override
    public MatrixIterator iterator(boolean rowMajor, int minRow, int minCol,
            int maxRow, int maxCol) {
        return data.iterator(rowMajor, minRow, minCol, maxRow, maxCol);
    }

    @Override
    public int getNumRows() {
        return data.getNumRows();
    }

    @Override
    public int getNumCols() {
        return data.getNumCols();
    }

    @Override
    public void setNumRows(int numRows) {
        int oldValue = getNumRows();
        data.setNumRows(numRows);
        p.firePropertyChange(ROWS.toString(), oldValue, numRows);
    }

    @Override
    public void setNumCols(int numCols) {
        int oldValue = getNumCols();
        data.setNumCols(numCols);
        p.firePropertyChange(COLUMNS.toString(), oldValue, numCols);
    }

    @Override
    public int getNumElements() {
        return data.getNumElements();
    }

    @Override
    public void set(ReshapeMatrix64F A) {
        data.set(A);
        lp.fireListItemChangedChange(DATA, 0, getNumRows(), null, A);
    }

    @Override
    public <T extends ReshapeMatrix64F> T copy() {
        return data.copy();
    }

    @Override
    public void print() {
        data.print();
    }

    @Override
    public void addPropertyChangeListener(DataProperty property,
            PropertyChangeListener listener) {
        p.addPropertyChangeListener(property.toString(), listener);
    }

    @Override
    public void removePropertyChangeListener(DataProperty property,
            PropertyChangeListener listener) {
        p.removePropertyChangeListener(property.toString(), listener);
    }

}
