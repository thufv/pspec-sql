package edu.thu.ss.xml.analyzer;

import java.util.Iterator;
import java.util.Set;

import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.DataCategoryRef;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategoryContainer;
import edu.thu.ss.xml.pojo.UserCategoryRef;

public class RuleSimplifier extends BaseRuleAnalyzer {
	@Override
	protected boolean analyzeRule(Rule rule, UserCategoryContainer users, DataCategoryContainer datas) {
		simplifyUserCategories(rule.getUserRefs());
		simplifyDataCategories(rule.getDataRefs());
		return false;
	}

	private void simplifyUserCategories(Set<UserCategoryRef> users) {
		Iterator<UserCategoryRef> it = users.iterator();
		while (it.hasNext()) {
			UserCategoryRef ref = it.next();
			boolean removable = false;
			for (UserCategoryRef ref2 : users) {
				if (ref.getUser().descedantOf(ref2.getUser())) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
			}
		}
	}

	private void simplifyDataCategories(Set<DataCategoryRef> datas) {
		Iterator<DataCategoryRef> it = datas.iterator();
		while (it.hasNext()) {
			DataCategoryRef ref = it.next();
			boolean removable = false;
			for (DataCategoryRef ref2 : datas) {
				if (ref.getData().descedantOf(ref2.getData())) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
			}
		}
	}

	@Override
	public boolean stopOnError() {
		return false;
	}

	@Override
	public String errorMsg() {
		return "";
	}

}
