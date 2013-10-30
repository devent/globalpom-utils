/*
 * Copyright 2013 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of prefdialog-api.
 *
 * prefdialog-api is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * prefdialog-api is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with prefdialog-api. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.globalpom.textposition;

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
