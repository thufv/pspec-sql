package edu.thu.ss.spec.lang.analyzer.rule;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserContainer;

public abstract class BaseRuleAnalyzer extends BasePolicyAnalyzer {

	protected Rule currentRule;
	protected String ruleId;
	protected Policy policy;

	@Override
	public boolean analyze(Policy policy) {
		boolean error = false;
		this.policy = policy;
		for (Rule rule : policy.getRules()) {
			this.currentRule = rule;
			this.ruleId = rule.getId();
			error = error || this.analyzeRule(rule, policy.getUserContainer(), policy.getDataContainer());
		}
		return error;
	}

	protected abstract boolean analyzeRule(Rule rule, UserContainer users, DataContainer datas);

}
