/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
