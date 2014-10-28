package edu.thu.ss.lang.util;

import java.util.Set;

public class SetUtil {

	public enum SetRelation {
		contain, intersect, disjoint
	}

	public static <T> boolean intersects(Set<T> set1, Set<T> set2) {
		for (T t : set1) {
			if (set2.contains(t)) {
				return true;
			}
		}
		return false;
	}

	public static <T> boolean contains(Set<T> set1, Set<T> set2) {
		return set1.containsAll(set2);
	}

	public static <T> SetRelation relation(Set<T> set1, Set<T> set2) {
		boolean match = false;
		for (T t2 : set2) {
			if (!set1.contains(t2)) {
				if (match) {
					return SetRelation.intersect;
				}
			} else {
				match = true;
			}
		}
		if (match) {
			return SetRelation.contain;
		} else {
			return SetRelation.disjoint;
		}
	}

}
