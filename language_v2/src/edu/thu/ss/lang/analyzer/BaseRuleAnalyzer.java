package edu.thu.ss.lang.analyzer;

import edu.thu.ss.lang.pojo.Policy;
import edu.thu.ss.lang.xml.XMLDataCategoryContainer;
import edu.thu.ss.lang.xml.XMLRule;
import edu.thu.ss.lang.xml.XMLUserCategoryContainer;

public abstract class BaseRuleAnalyzer extends BasePolicyAnalyzer {

	protected XMLRule currentRule;
	protected String ruleId;

	@Override
	public boolean analyze(Policy policy) {
		boolean error = false;
		for (XMLRule rule : policy.getRules()) {
			this.currentRule = rule;
			this.ruleId = rule.getId();
			error = error || this.analyzeRule(rule, policy.getUsers(), policy.getDatas());
		}
		return error;
	}

	protected abstract boolean analyzeRule(XMLRule rule, XMLUserCategoryContainer users, XMLDataCategoryContainer datas);

}
