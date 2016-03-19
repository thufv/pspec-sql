package edu.thu.ss.spec.lang.pojo;

import edu.thu.ss.spec.util.PSpecUtil;

public class AssociatedDataAccess {

	private DataAccess[] accesses;

	public AssociatedDataAccess(DataAccess[] accesses) {
		this.accesses = accesses;

	}

	public int length() {
		return accesses.length;
	}

	public DataAccess get(int i) {
		return accesses[i];
	}

	public boolean contains(DataAccess access) {
		for (int i = 0; i < accesses.length; i++) {
			if (accesses[i].equals(access)) {
				return true;
			}
		}
		return false;
	}

	public boolean subsumedBy(AssociatedDataAccess another) {
		if (this.length() > another.length()) {
			return false;
		}
		for (DataAccess access : accesses) {
			if (!another.contains(access)) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		return "(" + PSpecUtil.format(accesses, ", ") + ")";

	}

}
