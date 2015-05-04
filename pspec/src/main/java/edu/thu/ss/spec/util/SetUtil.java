package edu.thu.ss.spec.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.HierarchicalObject;

/**
 * Utility class for set operations
 * @author luochen
 *
 */
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

	/**
	 * return relation of set1 and set2
	 * @param set1
	 * @param set2
	 * @return {@link SetRelation}
	 */
	public static <T> SetRelation relation(Set<T> set1, Set<T> set2) {
		boolean contain = true;
		boolean intersect = false;
		for (T t2 : set2) {
			if (!set1.contains(t2)) {
				contain = false;
				if (intersect) {
					return SetRelation.intersect;
				}
			} else {
				intersect = true;
				if (!contain) {
					return SetRelation.intersect;
				}
			}
		}
		if (contain) {
			return SetRelation.contain;
		} else if (intersect) {
			return SetRelation.intersect;
		} else {
			return SetRelation.disjoint;
		}

	}

	public static <T> Set<T> intersect(Set<T> set1, Set<T> set2) {
		Set<T> result = new HashSet<T>(Math.min(set1.size(), set2.size()));
		for (T t1 : set1) {
			if (set2.contains(t1)) {
				result.add(t1);
			}
		}
		return result;
	}

	public static <T extends HierarchicalObject<T>> T bottom(T t1, T t2) {
		if (t1.ancestorOf(t2)) {
			return t2;
		} else if (t2.ancestorOf(t1)) {
			return t1;
		}
		return null;
	}

	public static <T> SetRelation containOrDisjoint(Set<T> set1, Set<T> set2) {
		for (T t2 : set2) {
			if (set1.contains(t2)) {
				return SetRelation.contain;
			} else {
				return SetRelation.disjoint;
			}
		}
		return SetRelation.contain;
	}

	/**
	 * Only smallest disjoint sets are retained
	 * 
	 * @param list1
	 * @param ops2
	 */
	public static <T> void mergeOperations(List<Set<T>> list1, Set<T> ops2) {
		if (list1.size() == 0) {
			list1.add(ops2);
			return;
		}
		if (ops2 == null) {
			return;
		}
		Iterator<Set<T>> it = list1.iterator();
		while (it.hasNext()) {
			Set<T> ops1 = it.next();
			if (ops1 == null) {
				it.remove();
			} else if (contains(ops2, ops1)) {
				return;
			} else if (contains(ops1, ops2)) {
				it.remove();
			}
		}
		list1.add(ops2);
	}

	public static String toString(int[] index, List<? extends ExpandedRule> rules) {
		StringBuilder sb = new StringBuilder();
		for (int i : index) {
			sb.append(rules.get(i).getRuleId());
			sb.append(' ');
		}
		return sb.toString();
	}

	public static <T> String format(Collection<T> collection, String col) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (T t : collection) {
			sb.append(t);
			if (i < collection.size() - 1) {
				sb.append(col);
			}
			i++;
		}
		return sb.toString();
	}

	public static <T> String format(T[] array, String col) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (T t : array) {
			sb.append(t);
			if (i < array.length - 1) {
				sb.append(col);
			}
			i++;
		}
		return sb.toString();
	}

	public static String spaces(int l) {
		StringBuilder sb = new StringBuilder(l * 2);
		for (int i = 0; i < 2 * l; i++) {
			sb.append(' ');
		}
		return sb.toString();
	}
}
