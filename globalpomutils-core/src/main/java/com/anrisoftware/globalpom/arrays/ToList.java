/*
 * Copyright 2013-2014 Erwin Müller <erwin.mueller@deventm.org>
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
package com.anrisoftware.globalpom.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Methods to convert an array to a list.
 * 
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
public class ToList {

	/**
	 * Returns a list based on the specified object.
	 * 
	 * @param obj
	 *            the {@link Object}.
	 * 
	 * @return the {@link List}.
	 */
	public static <E> List<E> toList(Object obj) {
		return toList(new ArrayList<E>(), obj);
	}

	/**
	 * @see #toList(Object)
	 * 
	 * @param list
	 *            the {@link List}.
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> toList(List<E> list, Object obj) {
		if (obj instanceof List) {
			return addAll(list, (List<E>) obj);
		}
		if (obj instanceof double[]) {
			return addAll(list, (double[]) obj);
		}
		if (obj instanceof long[]) {
			return addAll(list, (long[]) obj);
		}
		if (obj instanceof float[]) {
			return addAll(list, (float[]) obj);
		}
		if (obj instanceof int[]) {
			return addAll(list, (int[]) obj);
		}
		if (obj instanceof char[]) {
			return addAll(list, (char[]) obj);
		}
		if (obj instanceof byte[]) {
			return addAll(list, (byte[]) obj);
		}
		if (obj.getClass().isArray()) {
			return addAll(list, (List<E>) Arrays.asList((Object[]) obj));
		}
		list.add((E) obj);
		return list;
	}

	private static <E> List<E> addAll(List<E> list, List<E> listb) {
		list.addAll(listb);
		return list;
	}

	private static <E> List<E> addAll(List<E> list, double[] array) {
		@SuppressWarnings("unchecked")
		List<Number> numberlist = (List<Number>) list;
		for (double d : array) {
			numberlist.add(d);
		}
		return list;
	}

	private static <E> List<E> addAll(List<E> list, long[] array) {
		@SuppressWarnings("unchecked")
		List<Number> numberlist = (List<Number>) list;
		for (long d : array) {
			numberlist.add(d);
		}
		return list;
	}

	private static <E> List<E> addAll(List<E> list, float[] array) {
		@SuppressWarnings("unchecked")
		List<Number> numberlist = (List<Number>) list;
		for (float d : array) {
			numberlist.add(d);
		}
		return list;
	}

	private static <E> List<E> addAll(List<E> list, int[] array) {
		@SuppressWarnings("unchecked")
		List<Number> numberlist = (List<Number>) list;
		for (int d : array) {
			numberlist.add(d);
		}
		return list;
	}

	private static <E> List<E> addAll(List<E> list, char[] array) {
		@SuppressWarnings("unchecked")
		List<Character> numberlist = (List<Character>) list;
		for (char d : array) {
			numberlist.add(d);
		}
		return list;
	}

	private static <E> List<E> addAll(List<E> list, byte[] array) {
		@SuppressWarnings("unchecked")
		List<Number> numberlist = (List<Number>) list;
		for (byte d : array) {
			numberlist.add(d);
		}
		return list;
	}
}
