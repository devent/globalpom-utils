/*
 * Copyright 2013-2016 Erwin Müller <erwin.mueller@deventm.org>
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
/**
 * <h2>SerializableGradientPaint</h2>
 *
 * <p>
 * Decorates a gradient paint to enable serialization.
 * </p>
 *
 * <p>
 * Example with import static:
 * </p>
 *
 * <pre>
 * import static com.anrisoftware.globalpom.awtserializable.SerializableGradientPaint.decorateSerializableGradientPaint;
 *
 * GradientPaint paint = ...
 * decorateSerializableGradientPaint serializable = decorateSerializableGradientPaint(paint);
 * </pre>
 *
 * <h2>SerializableBasicStroke</h2>
 *
 * <p>
 * Decorates a basic stroke to enable serialization. The usage is the same
 * as the example above with SerializableGradientPaint.
 * </p>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 2.7
 */
package com.anrisoftware.globalpom.awtserializable;

