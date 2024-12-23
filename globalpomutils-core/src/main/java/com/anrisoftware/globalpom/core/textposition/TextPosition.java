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
package com.anrisoftware.globalpom.core.textposition;

/**
 * The text and icon positions.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.9
 */
public enum TextPosition {

	/**
	 * Show only the icon.
	 */
	ICON_ONLY,

	/**
	 * Show only the text.
	 */
	TEXT_ONLY,

	/**
	 * Show the text alongside the icon.
	 */
	TEXT_ALONGSIDE_ICON,

	/**
	 * Show the text is under the icon.
	 */
	TEXT_UNDER_ICON;
}
