/*
 * Copyright ${project.custom.year} Erwin Müller <erwin.mueller@anrisoftware.com>
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
package com.anrisoftware.globalpom.core.awtserializable;

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
     * @param stroke the {@link BasicStroke}.
     *
     * @return the {@link SerializableBasicStroke}.
     */
    public static SerializableBasicStroke decorate(BasicStroke stroke) {
        return new SerializableBasicStroke(stroke);
    }

    /**
     * Decorates the character set for serialization.
     *
     * @param stroke the {@link BasicStroke}.
     *
     * @return the {@link SerializableBasicStroke}.
     */
    public static SerializableBasicStroke decorateSerializableBasicStroke(BasicStroke stroke) {
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
     *
     * @param s the {@link Shape}
     *
     * @return the {@link Shape}.
     */
    public Shape createStrokedShape(Shape s) {
        return stroke.createStrokedShape(s);
    }

    /**
     * @see java.awt.BasicStroke#getLineWidth()
     *
     * @return the line width.
     */
    public float getLineWidth() {
        return stroke.getLineWidth();
    }

    /**
     * @see java.awt.BasicStroke#getEndCap()
     *
     * @return the end cap.
     */
    public int getEndCap() {
        return stroke.getEndCap();
    }

    /**
     * @see java.awt.BasicStroke#getLineJoin()
     *
     * @return the line join.
     */
    public int getLineJoin() {
        return stroke.getLineJoin();
    }

    /**
     * @see java.awt.BasicStroke#getMiterLimit()
     *
     * @return the miter limit.
     */
    public float getMiterLimit() {
        return stroke.getMiterLimit();
    }

    /**
     * @see java.awt.BasicStroke#getDashArray()
     *
     * @return the dash array.
     */
    public float[] getDashArray() {
        return stroke.getDashArray();
    }

    /**
     * @see java.awt.BasicStroke#getDashPhase()
     *
     * @return the dash phase.
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
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
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
        this.stroke = new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);
    }

}
