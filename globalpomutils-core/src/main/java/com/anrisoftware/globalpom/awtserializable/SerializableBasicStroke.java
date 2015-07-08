/*
 * Copyright 2013-2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of globalpomutils-core.
 *
 * globalpomutils-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * globalpomutils-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with globalpomutils-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.awtserializable;

import java.awt.BasicStroke;
import java.awt.Shape;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Serialize the specified basic stroke.
 *
 * @see BasicStroke
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.7
 */
public final class SerializableBasicStroke implements Externalizable {

    /**
     * Decorates the character set for serialization.
     *
     * @param stroke
     *            the {@link BasicStroke}.
     *
     * @return the {@link SerializableBasicStroke}.
     */
    public static SerializableBasicStroke decorate(BasicStroke stroke) {
        return new SerializableBasicStroke(stroke);
    }

    /**
     * Decorates the character set for serialization.
     *
     * @param stroke
     *            the {@link BasicStroke}.
     *
     * @return the {@link SerializableBasicStroke}.
     */
    public static SerializableBasicStroke decorateSerializableBasicStroke(
            BasicStroke stroke) {
        return new SerializableBasicStroke(stroke);
    }

    private BasicStroke stroke;

    /**
     * For serialization.
     */
    public SerializableBasicStroke() {
    }

    private SerializableBasicStroke(BasicStroke stroke) {
        this.stroke = stroke;
    }

    /**
     * Returns the serialized basic stroke.
     *
     * @return the {@link BasicStroke}.
     */
    public BasicStroke getStroke() {
        return stroke;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return stroke.toString();
    }

    /**
     * @see java.awt.BasicStroke#createStrokedShape(java.awt.Shape)
     */
    public Shape createStrokedShape(Shape s) {
        return stroke.createStrokedShape(s);
    }

    /**
     * @see java.awt.BasicStroke#getLineWidth()
     */
    public float getLineWidth() {
        return stroke.getLineWidth();
    }

    /**
     * @see java.awt.BasicStroke#getEndCap()
     */
    public int getEndCap() {
        return stroke.getEndCap();
    }

    /**
     * @see java.awt.BasicStroke#getLineJoin()
     */
    public int getLineJoin() {
        return stroke.getLineJoin();
    }

    /**
     * @see java.awt.BasicStroke#getMiterLimit()
     */
    public float getMiterLimit() {
        return stroke.getMiterLimit();
    }

    /**
     * @see java.awt.BasicStroke#getDashArray()
     */
    public float[] getDashArray() {
        return stroke.getDashArray();
    }

    /**
     * @see java.awt.BasicStroke#getDashPhase()
     */
    public float getDashPhase() {
        return stroke.getDashPhase();
    }

    /**
     * @see java.awt.BasicStroke#hashCode()
     */
    @Override
    public int hashCode() {
        return stroke.hashCode();
    }

    /**
     * @see java.awt.BasicStroke#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SerializableBasicStroke) {
            obj = ((SerializableBasicStroke) obj).getStroke();
        }
        return stroke.equals(obj);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat(stroke.getLineWidth());
        out.writeInt(stroke.getEndCap());
        out.writeInt(stroke.getLineJoin());
        out.writeFloat(stroke.getMiterLimit());
        out.writeBoolean(stroke.getDashArray() != null);
        if (stroke.getDashArray() != null) {
            out.writeObject(stroke.getDashArray());
        }
        out.writeFloat(stroke.getDashPhase());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        float width = in.readFloat();
        int cap = in.readInt();
        int join = in.readInt();
        float miterlimit = in.readFloat();
        boolean haveDash = in.readBoolean();
        float[] dash = null;
        if (haveDash) {
            dash = (float[]) in.readObject();
        }
        float dash_phase = in.readFloat();
        this.stroke = new BasicStroke(width, cap, join, miterlimit, dash,
                dash_phase);
    }

}
