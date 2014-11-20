package edu.thu.ss.spec.lang.analyzer.rule;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.xml.XMLRule;

public abstract class BaseRuleAnalyzer extends BasePolicyAnalyzer {

	protected XMLRule currentRule;
	protected String ruleId;

	@Override
	public boolean analyze(Policy policy) {
		boolean error = false;
		for (XMLRule rule : policy.getRules()) {
			this.currentRule = rule;
			this.ruleId = rule.getId();
			error = error || this.analyzeRule(rule, policy.getUserContainer(), policy.getDataContainer());
		}
		return error;
	}

	protected abstract boolean analyzeRule(XMLRule rule, UserContainer users, DataContainer datas);

}
