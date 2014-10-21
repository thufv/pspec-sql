package edu.thu.ss.xml.analyzer;

import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.Policy;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategoryContainer;

public abstract class BaseRuleAnalyzer implements PolicyAnalyzer {

	@Override
	public boolean analyze(Policy policy) {
		boolean error = false;
		for (Rule rule : policy.getRules()) {
			error = error || this.analyzeRule(rule, policy.getUsers(), policy.getDatas());
		}
		return error;
	}

	protected abstract boolean analyzeRule(Rule rule, UserCategoryContainer users, DataCategoryContainer datas);

}
