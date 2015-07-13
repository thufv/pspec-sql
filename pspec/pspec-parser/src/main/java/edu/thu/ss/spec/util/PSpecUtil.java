package edu.thu.ss.spec.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.RefErrorType;
import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.CategoryContainer;
import edu.thu.ss.spec.lang.pojo.CategoryRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.HierarchicalObject;
import edu.thu.ss.spec.lang.pojo.ObjectRef;
import edu.thu.ss.spec.lang.pojo.Rule;

/**
 * Utility class for set operations
 * @author luochen
 *
 */
public class PSpecUtil {

	private static final Logger logger = LoggerFactory.getLogger(PSpecUtil.class);

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

	public static <T extends Category<T>> boolean checkCategoryCycleReference(T category, T parent,
			Set<T> checked) {
		if (parent == null) {
			return false;
		}
		Set<String> set = new HashSet<>();
		set.add(category.getId());
		if (checked != null) {
			checked.add(category);
		}
		return checkCycleRefernece(parent, set, checked);
	}

	private static <T extends Category<T>> boolean checkCycleRefernece(T category, Set<String> ids,
			Set<T> checked) {
		if (ids.contains(category.getId())) {
			return true;
		}
		if (category.getParent() == null) {
			return false;
		}
		ids.add(category.getId());
		if (checked != null) {
			checked.add(category);
		}
		return checkCycleRefernece(category.getParent(), ids, checked);
	}

	public static <T extends Category<T>> boolean resolveCategoryRef(CategoryRef<T> ref,
			CategoryContainer<T> container, Rule rule, boolean refresh, EventTable table) {
		boolean error = false;
		if (ref.isResolved() && !refresh) {
			return error;
		}
		T category = container.get(ref.getRefid());
		if (category != null) {
			ref.setCategory(category);
		} else {
			table.onRuleRefError(RefErrorType.Category_Ref_Not_Exist, rule, ref, ref.getRefid());
			error = true;
		}
		for (ObjectRef excludeRef : ref.getExcludeRefs()) {
			T exclude = container.get(excludeRef.getRefid());
			if (exclude != null) {
				if (!checkExclusion(ref.getCategory(), exclude)) {
					ref.getExcludes().add(exclude);
				} else {
					//handle error
					logger.error("Excluded category: {} must be a sub-category of referenced category: {}",
							exclude.getId(), category.getId());
					table.onRuleRefError(RefErrorType.Category_Exclude_Invalid, rule, ref,
							excludeRef.getRefid());
					error = true;
				}
			} else {
				table.onRuleRefError(RefErrorType.Category_Exclude_Not_Exist, rule, ref,
						excludeRef.getRefid());
				error = true;
			}
		}
		if (!error) {
			ref.materialize(container);
		}
		return error;
	}

	public static <T extends Category<T>> boolean checkExclusion(T category, T exclude) {
		if (category == null) {
			return true;
		}
		if (!category.ancestorOf((T) exclude) || category.equals(exclude)) {
			return true;
		}
		return false;
	}
}
