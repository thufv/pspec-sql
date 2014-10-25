package edu.thu.ss.xml.analyzer;

import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.xml.pojo.CategoryRef;
import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategoryContainer;

public class RuleSimplifier extends BaseRuleAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(RuleSimplifier.class);

	@Override
	protected boolean analyzeRule(Rule rule, UserCategoryContainer users, DataCategoryContainer datas) {
		simplifyCategories(rule.getUserRefs(), rule.getId());
		simplifyCategories(rule.getDataRefs(), rule.getId());
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends CategoryRef> void simplifyCategories(Set<T> categories, String id) {
		Iterator<T> it = categories.iterator();
		while (it.hasNext()) {
			T ref1 = it.next();
			boolean removable = false;
			for (T ref2 : categories) {
				if (ref1.getCategory().descedantOf(ref2.getCategory()) && ref1 != ref2) {
					removable = true;
					break;
				}
			}
			if (removable) {
				it.remove();
				logger.warn("{} is removed from rule: {} since it is redundant.", ref1, id);
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
