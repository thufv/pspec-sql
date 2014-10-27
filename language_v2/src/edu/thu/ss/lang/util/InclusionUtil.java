package edu.thu.ss.lang.util;

import edu.thu.ss.lang.pojo.DataAssociation;
import edu.thu.ss.lang.pojo.DataCategoryRef;
import edu.thu.ss.lang.pojo.Desensitization;
import edu.thu.ss.lang.pojo.Restriction;
import edu.thu.ss.lang.pojo.UserCategoryRef;

public class InclusionUtil {

	public static boolean includes(UserCategoryRef user1, UserCategoryRef user2) {
		return user1.getMaterialized().containsAll(user2.getMaterialized());
	}

	public static boolean includes(DataCategoryRef data1, DataCategoryRef data2) {
		if (!data1.getAction().ancestorOf(data2.getAction())) {
			return false;
		}
		return data1.getMaterialized().containsAll(data2.getMaterialized());
	}

	public static boolean includes(DataAssociation association1, DataAssociation association2) {
		if (association1.getDataRefs().size() != association2.getDataRefs().size()) {
			return false;
		}
		for (DataCategoryRef data2 : association2.getDataRefs()) {
			boolean match = false;
			for (DataCategoryRef data1 : association1.getDataRefs()) {
				if (includes(data1, data2)) {
					match = true;
					break;
				}
			}
			if (!match) {
				return false;
			}
		}
		for (DataCategoryRef data1 : association1.getDataRefs()) {
			boolean match = false;
			for (DataCategoryRef data2 : association2.getDataRefs()) {
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
	 * test whether res1 is loose than res2, such that res2 can be removed.
	 * i.e., every restricted data category is res1 must be more restricted in
	 * res2
	 * 
	 * @param res1
	 * @param res2
	 * @return
	 */
	public static boolean looserThan(Restriction res1, Restriction res2) {
		if (res2.isForbid()) {
			return true;
		}
		if (res1.isForbid()) {
			return false;
		}

		for (Desensitization de1 : res1.getDesensitizations()) {
			for (DataCategoryRef ref1 : de1.getDataRefs()) {
				boolean match = false;
				for (Desensitization de2 : res2.getDesensitizations()) {
					if (de2.getDataRefs().contains(ref1)) {
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
	 * check whether res1 is stricter than res2
	 * 
	 * @param res1
	 * @param res2
	 * @return
	 */
	public static boolean singleStricterThan(Restriction res1, Restriction res2) {
		if (res1.isForbid()) {
			return true;
		}
		if (res2.isForbid()) {
			return false;
		}
		Desensitization de1 = res1.getDesensitizations().iterator().next();
		Desensitization de2 = res2.getDesensitizations().iterator().next();
		if (de2.getOperations().containsAll(de1.getOperations())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean includes(Desensitization de1, Desensitization de2) {
		if (de1.isDefaultOperation()) {
			return true;
		}
		if (de2.isDefaultOperation()) {
			return false;
		}
		return de1.getOperations().contains(de2.getOperations());
	}
}
