package edu.thu.ss.spec.util;

import edu.thu.ss.spec.lang.pojo.DataRef;

public class GlobalInclusionUtil extends InclusionUtil {

	public static final GlobalInclusionUtil instance = new GlobalInclusionUtil();

	public boolean includes(DataRef data1, DataRef data2) {
		if (data2.isGlobal() && !data1.isGlobal()) {
			return false;
		}
		if (!data1.getAction().ancestorOf(data2.getAction())) {
			return false;
		}
		return PSpecUtil.contains(data1.getMaterialized(), data2.getMaterialized());
	}

	@Override
	public boolean isGlobal(DataRef ref) {
		return ref.isGlobal();
	}
}
