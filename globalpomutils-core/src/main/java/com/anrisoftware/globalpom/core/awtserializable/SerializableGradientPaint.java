/*
 * Copyright 2013-2022 Erwin Müller <erwin.mueller@anrisoftware.com>
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

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Serialize the specified gradient paint.
 *
 * @see GradientPaint
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 2.7
 */
public class SerializableGradientPaint implements Externalizable {

    /**
     * Decorates the character set for serialization.
     *
     * @param paint the {@link GradientPaint}.
     *
     * @return the {@link SerializableGradientPaint}.
     */
    public static SerializableGradientPaint decorate(GradientPaint paint) {
        return new SerializableGradientPaint(paint);
    }

    /**
     * Decorates the character set for serialization.
     *
     * @param paint the {@link GradientPaint}.
     *
     * @return the {@link SerializableGradientPaint}.
     */
    public static SerializableGradientPaint decorateSerializableGradientPaint(GradientPaint paint) {
        return new SerializableGradientPaint(paint);
    }

    private transient GradientPaint paint;

    /**
     * For serialization.
     */
    public SerializableGradientPaint() {
    }

    private SerializableGradientPaint(GradientPaint paint) {
        this.paint = paint;
    }

    /**
     * Returns the serialized gradient paint.
     *
     * @return the {@link GradientPaint}.
     */
    public GradientPaint getPaint() {
        return paint;
    }

    @Override
    public String toString() {
        return paint.toString();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return paint.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SerializableGradientPaint) {
            obj = ((SerializableGradientPaint) obj).getPaint();
        }
        return paint.equals(obj);
    }

    /**
     * @see java.awt.GradientPaint#getPoint1()
     *
     * @return the {@link Point2D}
     */
    public Point2D getPoint1() {
        return paint.getPoint1();
    }

    /**
     * @see java.awt.GradientPaint#getColor1()
     *
     * @return the {@link Color}
     */
    public Color getColor1() {
        return paint.getColor1();
    }

    /**
     * @see java.awt.GradientPaint#getPoint2()
     *
     * @return the {@link Point2D}
     */
    public Point2D getPoint2() {
        return paint.getPoint2();
    }

    /**
     * @see java.awt.GradientPaint#getColor2()
     *
     * @return the {@link Color}
     */
    public Color getColor2() {
        return paint.getColor2();
    }

    /**
     * @see java.awt.GradientPaint#isCyclic()
     *
     * @return is cyclic.
     */
    public boolean isCyclic() {
        return paint.isCyclic();
    }

    /**
     * @see java.awt.GradientPaint#createContext(java.awt.image.ColorModel,
     *      java.awt.Rectangle, java.awt.geom.Rectangle2D,
     *      java.awt.geom.AffineTransform, java.awt.RenderingHints)
     *
     * @param cm           the {@link ColorModel}
     *
     * @param deviceBounds the {@link Rectangle}
     *
     * @param userBounds   the {@link Rectangle2D}
     *
     * @param xform        the {@link AffineTransform}
     *
     * @param hints        {@link RenderingHints}
     *
     * @return the {@link PaintContext}
     */
    public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds,
            AffineTransform xform, RenderingHints hints) {
        return paint.createContext(cm, deviceBounds, userBounds, xform, hints);
    }

    /**
     * @see java.awt.GradientPaint#getTransparency()
     *
     * @return the transparency.
     */
    public int getTransparency() {
        return paint.getTransparency();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(paint.getColor1());
        out.writeObject(paint.getColor2());
        out.writeObject(paint.getPoint1());
        out.writeObject(paint.getPoint2());
        out.writeBoolean(paint.isCyclic());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Color color1 = (Color) in.readObject();
        Color color2 = (Color) in.readObject();
        Point2D point1 = (Point2D) in.readObject();
        Point2D point2 = (Point2D) in.readObject();
        boolean cyclic = in.readBoolean();
        this.paint = new GradientPaint(point1, color1, point2, color2, cyclic);
    }

}
