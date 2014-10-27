package edu.thu.ss.lang.analyzer;

import edu.thu.ss.lang.pojo.DataCategoryContainer;
import edu.thu.ss.lang.pojo.Policy;
import edu.thu.ss.lang.pojo.Rule;
import edu.thu.ss.lang.pojo.UserCategoryContainer;

public abstract class BaseRuleAnalyzer extends BasePolicyAnalyzer {

	protected Rule currentRule;
	protected String ruleId;

	@Override
	public boolean analyze(Policy policy) {
		boolean error = false;
		for (Rule rule : policy.getRules()) {
			this.currentRule = rule;
			this.ruleId = rule.getId();
			error = error || this.analyzeRule(rule, policy.getUsers(), policy.getDatas());
		}
		return error;
	}

	protected abstract boolean analyzeRule(Rule rule, UserCategoryContainer users, DataCategoryContainer datas);

}
