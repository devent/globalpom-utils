/*
 * Copyright 2013-2025 Erwin Müller <erwin.mueller@anrisoftware.com>
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
 * import static com.anrisoftware.globalpom.core.external.awtserializable.SerializableGradientPaint.decorateSerializableGradientPaint;
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
package com.anrisoftware.globalpom.core.awtserializable;
