package edu.thu.ss.lang.util;

import java.util.Iterator;
import java.util.Set;

import edu.thu.ss.lang.pojo.Action;
import edu.thu.ss.lang.pojo.DataCategory;
import edu.thu.ss.lang.pojo.Desensitization;
import edu.thu.ss.lang.pojo.Restriction;
import edu.thu.ss.lang.xml.XMLDataAssociation;
import edu.thu.ss.lang.xml.XMLDataCategoryRef;
import edu.thu.ss.lang.xml.XMLDesensitization;
import edu.thu.ss.lang.xml.XMLRestriction;
import edu.thu.ss.lang.xml.XMLUserCategoryRef;

public class InclusionUtil {

	public static boolean includes(XMLUserCategoryRef user1, XMLUserCategoryRef user2) {
		return SetUtil.contains(user1.getMaterialized(), user2.getMaterialized());
	}

	public static boolean includes(Action action1, Action action2) {
		return action1.ancestorOf(action2);
	}

	public static boolean includes(XMLDataCategoryRef data1, XMLDataCategoryRef data2) {
		if (!data1.getAction().ancestorOf(data2.getAction())) {
			return false;
		}
		return SetUtil.contains(data1.getMaterialized(), data2.getMaterialized());
	}

	public static boolean includes(XMLDataAssociation association1, XMLDataAssociation association2) {
		if (association1.getDataRefs().size() != association2.getDataRefs().size()) {
			return false;
		}
		for (XMLDataCategoryRef data2 : association2.getDataRefs()) {
			boolean match = false;
			for (XMLDataCategoryRef data1 : association1.getDataRefs()) {
				if (includes(data1, data2)) {
					match = true;
					break;
				}
			}
			if (!match) {
				return false;
			}
		}
		for (XMLDataCategoryRef data1 : association1.getDataRefs()) {
			boolean match = false;
			for (XMLDataCategoryRef data2 : association2.getDataRefs()) {
				if (includes(data1, data2)) {
					match = true;
					break;
				}
			}
			if (!match) {
				return false;
			}
		}
		return true;
	}

	/**
	 * test whether res1 is stricter than res2, such that res1 can be removed in
	 * a single rule.
	 * i.e., every restricted data category is res1 must be more restricted in
	 * res2
	 * 
	 * @param res1
	 * @param res2
	 * @return
	 */
	public static boolean stricterThan(XMLRestriction res1, XMLRestriction res2) {
		if (res1.isForbid()) {
			return true;
		}
		if (res2.isForbid()) {
			return false;
		}
		for (XMLDesensitization de2 : res2.getDesensitizations()) {
			for (XMLDataCategoryRef ref2 : de2.getDataRefs()) {
				boolean match = false;
				for (XMLDesensitization de1 : res1.getDesensitizations()) {
					if (de1.getDataRefs().contains(ref2)) {
						match = true;
						if (!includes(de1, de2)) {
							return false;
						}
					}
				}
				if (!match) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * test whether list1 is stricter than list2,
	 * i.e., every res1 in list1 is stricter than at least one res2 in list2
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static boolean stricterThan(Restriction[] list1, Restriction[] list2) {
		for (Restriction res1 : list1) {
			if (res1.isForbid()) {
				continue;
			}
			boolean match = false;
			for (Restriction res2 : list2) {
				if (stricterThan(res1, res2)) {
					match = true;
					break;
				}
			}
			if (!match) {
				return false;
			}
		}
		return true;
	}

	/**
	 * test whether res1 is stricter than res2, i.e., every restricted data
	 * category in res2 must be more restricted in res1
	 * 
	 * @param res1
	 * @param res2
	 * @return
	 */
	public static boolean stricterThan(Restriction res1, Restriction res2) {
		if (res1.isForbid()) {
			return true;
		}
		if (res2.isForbid()) {
			return false;
		}
		for (Desensitization de2 : res2.getDesensitizations()) {
			for (Desensitization de1 : res1.getDesensitizations()) {
				if (SetUtil.intersects(de1.getDatas(), de2.getDatas())) {
					if (!includes(de1, de2)) {
						return false;
					}
				}
			}
		}
		for (Desensitization de2 : res2.getDesensitizations()) {
			for (DataCategory data2 : de2.getDatas()) {
				boolean match = false;
				for (Desensitization de1 : res1.getDesensitizations()) {
					if (de1.getDatas().contains(data2)) {
						match = true;
						continue;
					}
				}
				if (!match) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * check res1 is stricter than one of res2
	 * 
	 * @param res1
	 * @param res2
	 * @return
	 */
	public static boolean stricterThan(Restriction res1, Restriction[] list2) {
		if (res1.isForbid()) {
			return true;
		}
		Desensitization de1 = res1.getDesensitization();
		for (Restriction res2 : list2) {
			if (res2.isForbid()) {
				continue;
			}
			if (stricterThan(de1, res2.getDesensitizations())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Side effect, des2 can be simplified.
	 * 
	 * @param data1
	 * @param de1
	 * @param des2
	 * @return
	 */
	public static boolean stricterThan(Desensitization de1, Set<Desensitization> des2) {
		boolean match = true;
		Set<DataCategory> data1 = de1.getDatas();
		Iterator<Desensitization> it = des2.iterator();
		while (it.hasNext()) {
			Desensitization de2 = it.next();
			if (SetUtil.contains(data1, de2.getDatas())) {
				if (includes(de2, de1)) {
					//de2 can be removed;
					it.remove();
				} else {
					match = false;
				}
			} else {
				match = false;
			}
		}
		return match;
	}

	public static boolean includes(Desensitization de1, Desensitization de2) {
		if (de1.isDefaultOperation()) {
			return true;
		}
		if (de2.isDefaultOperation()) {
			for (DataCategory data2 : de2.getDatas()) {
				if (!SetUtil.contains(de1.getOperations(), data2.getOperations())) {
					return false;
				}
			}
			return true;
		}
		return SetUtil.contains(de1.getOperations(), de2.getOperations());
	}

	/*
	
	/**
	 * check whether res1 is stricter than res2
	 * 
	 * @param res1
	 * @param res2
	 * @return
	 *
	public static boolean singleStricterThan(Restriction res1, Restriction res2) {
		if (res1.isForbid()) {
			return true;
		}
		if (res2.isForbid()) {
			return false;
		}
		Desensitization de1 = res1.getDesensitizations().iterator().next();
		Desensitization de2 = res2.getDesensitizations().iterator().next();
		return includes(de2, de1);
	}

	

	public static boolean stricterThan(List<Restriction> list1, List<Restriction> list2) {
		for (Restriction res1 : list1) {
			if (res1.isForbid()) {
				continue;
			}
			boolean match = false;
			for (Restriction res2 : list2) {
				if (stricterThan(res1, res2)) {
					match = true;
					break;
				}
			}
			if (!match) {
				return false;
			}
		}
		return true;
	}

	
	*/

}
