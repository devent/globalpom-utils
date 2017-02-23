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
package com.anrisoftware.globalpom.core.arrays;

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
