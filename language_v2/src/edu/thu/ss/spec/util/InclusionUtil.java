package edu.thu.ss.spec.util;

import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.UserRef;

public class InclusionUtil {

	public static final InclusionUtil instance = new InclusionUtil();

	/**
	 * test user1 includes user2
	 * @param user1
	 * @param user2
	 * @return boolean
	 */
	public boolean includes(UserRef user1, UserRef user2) {
		return SetUtil.contains(user1.getMaterialized(), user2.getMaterialized());
	}

	/**
	 * test action1 includes action2
	 * @param action1
	 * @param action2
	 * @return boolean
	 */
	public boolean includes(Action action1, Action action2) {
		return action1.ancestorOf(action2);
	}

	public boolean isGlobal(DataRef ref) {
		return false;
	}

	/**
	 * test data1 includes data2
	 * @param data1
	 * @param data2
	 * @return boolean
	 */
	public boolean includes(DataRef data1, DataRef data2) {
		if (!includes(data1.getAction(), data2.getAction())) {
			return false;
		}
		return SetUtil.contains(data1.getMaterialized(), data2.getMaterialized());
	}

	/**
	 * test whether res2 is stricter than res1, such that res1 can be removed in
	 * a single rule.
	 * i.e., every restricted data category is res1 must be more restricted in
	 * res2
	 * scope: within a single rule
	 * @param res1
	 * @param res2
	 * @return boolean
	 */
	public boolean innerStricterThan(Restriction res1, Restriction res2) {
		if (res1.isForbid()) {
			return true;
		}
		if (res2.isForbid()) {
			return false;
		}
		Desensitization[] des1 = res1.getDesensitizations();
		Desensitization[] des2 = res2.getDesensitizations();
		for (int i = 0; i < des1.length; i++) {
			if (des1[i] == null) {
				continue;
			}
			if (des2[i] == null) {
				return false;
			}
			if (!operationIncludes(des1[i], des2[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * test res1 is stricter than res2, both res1 and res2 are single.
	 * @param res1
	 * @param res2
	 * @return boolean
	 */
	public boolean singleStricterThan(Restriction res1, Restriction res2) {
		if (res1.isForbid()) {
			return true;
		}
		if (res2.isForbid()) {
			return false;
		}
		Desensitization de1 = res1.getDesensitization();
		Desensitization de2 = res2.getDesensitization();
		return operationIncludes(de2, de1);
	}

	/**
	 * test whether desensitize operations in de1 includes de2
	 * @param de1
	 * @param de2
	 * @return boolean
	 */
	public boolean operationIncludes(Desensitization de1, Desensitization de2) {
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


	/**
	 * test whether list1 is stricter than list2,
	 * i.e., every res1 in list1 is stricter than at least one res2 in list2
	 * 
	 * @param list1
	 * @param list2
	 * @return boolean
	 *
	public boolean stricterThan(Restriction[] list1, Restriction[] list2) {
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
	 * @return boolean
	 *
	public boolean stricterThan(Restriction res1, Restriction res2) {
		if (res1.isForbid()) {
			return true;
		}
		if (res2.isForbid()) {
			return false;
		}

		for (Desensitization de2 : res2.getDesensitizations()) {
			for (DataRef ref2 : de2.getDataRefs()) {
				boolean match = false;
				for (Desensitization de1 : res1.getDesensitizations()) {
					if (scopeIncludes(de1, ref2)) {
						if (!operationIncludes(de2, de1)) {
							return false;
						}
						match = true;
						break;
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
	 * whether the scope of de1 includes de2.
	 * scope means restricted {@link DataCategory} in {@link Desensitization}.
	 * @param de1
	 * @param de2
	 * @return boolean
	 *
	public boolean scopeIncludes(Desensitization de1, Desensitization de2) {
		for (DataRef ref2 : de2.getDataRefs()) {
			if (!scopeIncludes(de1, ref2)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * whether the scope of de1 includes ref2
	 * @param de1
	 * @param ref2
	 * @return boolean
	 *
	public boolean scopeIncludes(Desensitization de1, DataRef ref2) {
		boolean match = false;
		for (DataRef ref1 : de1.getDataRefs()) {
			if (includes(ref1, ref2)) {
				match = true;
				break;
			}
		}
		if (!match) {
			return false;
		}
		return true;
	}
	*/

}
